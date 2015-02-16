package org.pharmgkb;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the excel file passed to it for Sample data and outputs the data found to another excel file.
 *
 * @author Ryan Whaley
 */
public class ExcelParser {
  private static final Pattern sf_projectFilenameRegex = Pattern.compile("project(\\d+).xlsx");
  private static final Logger sf_logger = LoggerFactory.getLogger(ExcelParser.class);
  private File m_file = null;
  private Workbook m_workbook = null;
  private Sheet m_dataSheet = null;

  /**
   * Default constructor. This will make a parser for the blank template file, only good for analyzing column headers.
   * @throws Exception can occur from File IO
   */
  public ExcelParser() throws Exception {
    init(new File(getClass().getResource("ICPC_Submission_Template.xlsx").toURI()));
  }

  /**
   * Constructor. This will make a parser for the given template file, assumed to contain sample data.
   * @param file an Excel file (.xls .xlsx) containing sample data
   * @throws Exception can occur from File IO
   */
  public ExcelParser(File file) throws Exception {
    init(file);
  }

  /**
   * Initialize all the internal properties for this instance
   * @param file the file to parse
   * @throws Exception can occur from File IO
   */
  private void init(File file) throws Exception {
    if (file == null || !file.exists()) {
      throw new Exception("No file specified");
    }
    setFile(file);

    try(InputStream inputStream = new FileInputStream(file)) {
      setWorkbook(WorkbookFactory.create(inputStream));
    }

    setDataSheet(getWorkbook().getSheet(IcpcUtils.DATA_SHEET_NAME));
    if (getDataSheet() == null) {
      throw new Exception("Required worksheet "+IcpcUtils.DATA_SHEET_NAME+" not found");
    }
  }

  public void clearSubjects() throws PgkbException {
    Matcher m = sf_projectFilenameRegex.matcher(getFile().getName());
    if (!m.matches()) {
      throw new PgkbException("Project name not in right format "+getFile());
    }

    Session session = null;
    Integer projectId = Integer.valueOf(m.group(1));
    sf_logger.info("Clearing subjects for project "+projectId);

    try {
      session = HibernateUtils.getSession();
      List rez = session.createQuery("select s.id from Sample s where s.project=:pid")
              .setInteger("pid", Integer.valueOf(m.group(1))).list();
      for (Object result : rez) {
        session.delete(session.get(Sample.class, (String)result));
      }
      HibernateUtils.commit(session);
      sf_logger.info("Removed "+rez.size()+" records");
    }
    finally {
      HibernateUtils.close(session);
    }
  }

  /**
   * Parses sample data from the excel workbook and saves it to the database. Will copy the input file to the specified
   * output file.
   * @param outputFile file to copy workbook content to
   * @throws Exception can occur from DB or IO
   */
  public void parse(File outputFile) throws Exception {
    sf_logger.info("Parsing excel workbook "+getFile());
    sf_logger.info("writing output to "+outputFile);

    SubjectIterator subjectIterator = new SubjectIterator(getDataSheet());
    Session session = null;

    try {
      session = HibernateUtils.getSession();
      subjectIterator.parseHeading(session);

      while (subjectIterator.hasNext()) {
        Sample sample = subjectIterator.next();

        if (sf_logger.isDebugEnabled()) {
          sf_logger.debug("Loaded subject: "+ sample.getSubjectId());
        }

        session.save(sample);
      }
      HibernateUtils.commit(session);
      sf_logger.info("Processed "+subjectIterator.getCurrentRow()+" rows");
    }
    catch (Exception ex) {
      sf_logger.error("Error saving subjects for "+getFile(), ex);
    }
    finally {
      HibernateUtils.close(session);
    }
  }

  /**
   * Return the second row of the excel sheet as a List of Strings. This is good for analyzing what properties this
   * sheet has.
   * @return a List of String names for the properties in this sheet
   */
  public List<String> analyze() {
    List<String> titles = Lists.newArrayList();
    Row row = getDataSheet().getRow(1);
    for (Cell cell : row) {
      titles.add(cell.getStringCellValue());

      if (StringUtils.isBlank(cell.getStringCellValue())) {
        sf_logger.warn("EMPTY CELL at " + getFile().getName() + ":" + ExcelUtils.getAddress(cell));
      }
    }
    return titles;
  }

  public File getFile() {
    return m_file;
  }

  public void setFile(File file) {
    m_file = file;
  }

  public Workbook getWorkbook() {
    return m_workbook;
  }

  public void setWorkbook(Workbook workbook) {
    m_workbook = workbook;
  }

  public Sheet getDataSheet() {
    return m_dataSheet;
  }

  public void setDataSheet(Sheet dataSheet) {
    m_dataSheet = dataSheet;
  }
}
