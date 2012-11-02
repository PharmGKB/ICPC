package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.sun.javafx.beans.annotations.NonNull;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.Session;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This report generator will dump all subjects and their properties to a single file.
 *
 * @author Ryan Whaley
 */
public class CombinedDataReport {
  private static final Logger sf_logger = Logger.getLogger(CombinedDataReport.class);
  private File m_outputFile = null;

  /**
   * Constructor
   * @param file file to write the report to, required
   */
  public CombinedDataReport(@NonNull File file) {
    Preconditions.checkNotNull(file);
    setOutputFile(file);
  }

  /**
   * Generate the report and save it to the file specified at construction.
   *
   * @throws PgkbException can occur if output file is not specified or if I/O operations fail
   */
  public void generate() throws PgkbException {
    Preconditions.checkNotNull(getOutputFile());
    Session session = null;
    FileOutputStream fileOutputStream = null;
    int currentRowIdx = 0;

    try {
      session = HibernateUtils.getSession();

      SXSSFWorkbook workbook = new SXSSFWorkbook(5);
      Sheet sheet = workbook.createSheet("ICPC Data");
      Row descripRow = sheet.createRow(currentRowIdx++);
      Row nameRow = sheet.createRow(currentRowIdx++);

      Map<String,Integer> propertyIndexMap = Maps.newHashMap();
      List rez = session.createQuery("from IcpcProperty ip order by ip.index")
          .list();
      for (Object result :rez) {
        IcpcProperty property = (IcpcProperty)result;
        propertyIndexMap.put(property.getName(), property.getIndex());

        if (sf_logger.isDebugEnabled()) {
          sf_logger.debug(property.getIndex()+": "+property.getDescription());
        }

        ExcelUtils.writeCell(descripRow, property.getIndex()-1, property.getDescription());
        ExcelUtils.writeCell(nameRow, property.getIndex()-1, property.getName());
      }

      rez = session.createQuery("select s.subjectId from Subject s order by s.project,s.subjectId").list();
      for (Object result : rez) {
        Subject subject = (Subject)session.get(Subject.class, (String)result);
        Row row = sheet.createRow(currentRowIdx++);

        for (String propertyName : subject.getProperties().keySet()) {
          if (propertyIndexMap.get(propertyName) != null) {
            Integer columnIdx = propertyIndexMap.get(propertyName)-1;
            ExcelUtils.writeCell(row, columnIdx, subject.getProperties().get(propertyName));
          }
          else {
            sf_logger.warn("Property not mapped "+propertyName);
          }
        }
      }

      sf_logger.info("writing to file " + getOutputFile());
      fileOutputStream = new FileOutputStream(getOutputFile());
      workbook.write(fileOutputStream);
    }
    catch (IOException ex) {
      throw new PgkbException("Error writing report to disk", ex);
    }
    finally {
      IOUtils.closeQuietly(fileOutputStream);
      HibernateUtils.close(session);
    }
  }

  /**
   * Gets the file to output the report to
   * @return the file to output the report to
   */
  public File getOutputFile() {
    return m_outputFile;
  }

  /**
   * Sets the file to output the report to
   * @param outputFile the file to output the report to
   */
  public void setOutputFile(File outputFile) {
    m_outputFile = outputFile;
  }
}
