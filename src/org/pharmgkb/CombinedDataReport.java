package org.pharmgkb;

import com.google.common.base.Preconditions;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * This report generator will dump all subjects and their properties to a single file.
 *
 * @author Ryan Whaley
 */
public class CombinedDataReport {
  private static final Logger sf_logger = LoggerFactory.getLogger(CombinedDataReport.class);
  private File m_outputFile = null;

  /**
   * Constructor
   * @param file file to write the report to, required
   */
  public CombinedDataReport(File file) {
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

    sf_logger.info("starting "+this.getClass().getSimpleName()+", writing to file " + getOutputFile());

    Session session = null;

    try(FileOutputStream out = new FileOutputStream(getOutputFile())) {
      int currentRowIdx = 0;
      session = HibernateUtils.getSession();

      Workbook workbook = new XSSFWorkbook();
      Sheet sheet = workbook.createSheet(IcpcUtils.DATA_SHEET_NAME);

      CellStyle cs = workbook.createCellStyle();
      cs.setWrapText(true);
      Font f = workbook.createFont();
      f.setBoldweight(Font.BOLDWEIGHT_BOLD);
      cs.setFont(f);

      Row nameRow = sheet.createRow(currentRowIdx++);
      Row descripRow = sheet.createRow(currentRowIdx++);
      Row formatRow = sheet.createRow(currentRowIdx++);

      descripRow.setHeightInPoints(30f);
      formatRow.setHeightInPoints(60f);

      for (Property property : Property.values()) {
        sf_logger.debug("{}: {}", property.ordinal(), property.getDisplayName());

        ExcelUtils.writeCell(descripRow, property.ordinal(), property.getDisplayName(), cs);
        ExcelUtils.writeCell(nameRow, property.ordinal(), property.getShortName());
        ExcelUtils.writeCell(formatRow, property.ordinal(), IcpcUtils.lookupFormat(session, property.getShortName()), cs);
      }

      List rez = session.createQuery("select s.subjectId from Sample s order by s.project,s.subjectId").list();
      int projectId = 0;
      for (Object result : rez) {
        Sample sample = (Sample)session.get(Sample.class, (String)result);
        if (projectId != sample.getProject()) {
          sf_logger.info("writing project {}", sample.getProject());
          projectId = sample.getProject();
        }
        Row row = sheet.createRow(currentRowIdx++);

        for (Property property : Property.values()) {
          try {
            Integer valueColIdx = property.ordinal();
            boolean isNumber = property.getValidator() == IcpcUtils.VALIDATOR_NUMBER;
            String propValue = sample.getProperties().get(property);

            // if it's blank, write NA
            if (IcpcUtils.isBlank(propValue)) {
              ExcelUtils.writeCell(row, valueColIdx, IcpcUtils.NA);
            }
            // if property is a number, try to write a Double to a Number formatted column
            else if (isNumber) {
              try {

                double numValue = Double.valueOf(propValue);
                if (property == Property.AGE) {
                  ExcelUtils.writeCell(row, valueColIdx, Math.floor(numValue), null);
                }
                else {
                  ExcelUtils.writeCell(row, valueColIdx, numValue, null);
                }

              } catch (NumberFormatException ex) {
                sf_logger.debug("Input string is not number: {}", propValue);
                ExcelUtils.writeCell(row, valueColIdx, propValue);
              }
            }
            // otherwise, treat it like plain text
            else {
              ExcelUtils.writeCell(row, valueColIdx, propValue);
            }
          }
          catch (Exception ex) {
            sf_logger.error("Error writing data for sample {}, property {}", result, property.getShortName());
          }
        }
      }

      workbook.write(out);
    }
    catch (Exception ex) {
      throw new PgkbException("Error writing report",ex);
    }
    finally {
      HibernateUtils.close(session);
    }
    sf_logger.info("done with {}",this.getClass().getSimpleName());
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
