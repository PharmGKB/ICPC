package org.pharmgkb;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 8/28/12
 */
public class ExcelParser {
  private static final Logger sf_logger = Logger.getLogger(ExcelParser.class);
  private static final String sf_dataSheetName = "Subject_level_Data";
  private File m_file = null;
  private Workbook m_workbook = null;
  private Sheet m_dataSheet = null;

  public ExcelParser(File file) throws Exception {
    if (file == null || !file.exists()) {
      throw new Exception("No file specified");
    }
    setFile(file);

    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      setWorkbook(WorkbookFactory.create(inputStream));
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }

    setDataSheet(getWorkbook().getSheet(sf_dataSheetName));
    if (getDataSheet() == null) {
      throw new Exception("Required worksheet "+sf_dataSheetName+" not found");
    }

    Row headerRow = getDataSheet().getRow(1);
  }

  public void parse(File outputFile) throws Exception {
    sf_logger.info("Parsing excel workbook "+getFile());
    sf_logger.info("writing output to "+outputFile);

    SubjectIterator subjectIterator = new SubjectIterator(getDataSheet());
    Session session = null;
    FileOutputStream fos = null;

    try {
      session = HibernateUtils.getSession();
      subjectIterator.parseHeading(session);

      while (subjectIterator.hasNext()) {

        Subject subject = subjectIterator.next();

        if (sf_logger.isDebugEnabled()) {
          sf_logger.debug("Loaded subject: "+subject.getSubjectId());
        }

        Subject existingSubject = (Subject)session.get(Subject.class, subject.getSubjectId());
        if (existingSubject != null) {
          session.delete(existingSubject);
        }

        session.save(subject);
      }
      HibernateUtils.commit(session);

      fos = new FileOutputStream(outputFile);
      getWorkbook().write(fos);
    }
    catch (Exception ex) {
      sf_logger.error("Error saving subjects for "+getFile(), ex);
    }
    finally {
      IOUtils.closeQuietly(fos);
      HibernateUtils.close(session);
    }
  }

  public List<String> analyze() {
    List<String> titles = Lists.newArrayList();
    Row row = getDataSheet().getRow(1);
    for (Cell cell : row) {
      titles.add(cell.getStringCellValue());

      if (StringUtils.isBlank(cell.getStringCellValue())) {
        sf_logger.warn("EMPTY CELL at "+getFile().getName()+":"+ ExcelUtils.getAddress(cell));
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
