package org.pharmgkb;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Parses the excel file passed to it for Sample data and outputs the data found to another excel file.
 *
 * @author Ryan Whaley
 */
public class ExcelParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(ExcelParser.class);
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

        Sample sample = subjectIterator.next();

        if (sf_logger.isDebugEnabled()) {
          sf_logger.debug("Loaded subject: "+ sample.getSubjectId());
        }

        session.save(sample);
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
        sf_logger.warn("EMPTY CELL at " + getFile().getName() + ":" + ExcelUtils.getAddress(cell));
      }
    }
    return titles;
  }

  public void loadFormats(Session session) {

    Row rowDescrip = getDataSheet().getRow(1);
    Row rowFormat = getDataSheet().getRow(2);

    for (int i=0; i<rowDescrip.getLastCellNum(); i++) {
      Cell cellDescrip = rowDescrip.getCell(i);
      Cell cellFormat = rowFormat.getCell(i);

      if (StringUtils.isBlank(cellDescrip.getStringCellValue())) {
        sf_logger.warn("EMPTY CELL at "+getFile().getName()+":"+ ExcelUtils.getAddress(cellDescrip));
        continue;
      }

      String descrip = cellDescrip.getStringCellValue();
      String format = cellFormat.getStringCellValue();

      String name = (String)session.createSQLQuery("select name from propertynames where descrip=:d")
              .setString("d", StringUtils.strip(descrip)).uniqueResult();

      int updateCount = session.createSQLQuery("update propertynames set format=:format where descrip=:descrip")
              .setString("format", format)
              .setString("descrip", descrip)
              .executeUpdate();

      if (updateCount>0) {
        sf_logger.info("{} updated with format", name);
      }
    }
    HibernateUtils.commit(session);
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
