package org.pharmgkb;

import com.google.common.base.Preconditions;
import org.apache.poi.ss.usermodel.Row;
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
public class CombinedDataReport extends AbstractReport {
  private static final Logger sf_logger = LoggerFactory.getLogger(CombinedDataReport.class);
  private static final String sf_filename = "icpc.combined.xlsx";
  private static final int sf_defaultColumnWidth = 15;
  public static final String DATA_SHEET_NAME = "Subject_level_Data";

  private Integer m_project = null;

  /**
   * Constructor
   * @param dir directory to write the report to, required
   * @param project the project to write. if null then write all projects. not required
   */
  public CombinedDataReport(File dir, Integer project) {
    super(sf_defaultColumnWidth);
    Preconditions.checkNotNull(dir);
    Preconditions.checkArgument(dir.isDirectory());
    setFile(new File(dir, sf_filename));

    if (project != null) {
      m_project = project;
    }
  }

  /**
   * Generate the report and save it to the file specified at construction.
   *
   * @throws PgkbException can occur if output file is not specified or if I/O operations fail
   */
  public void generate() throws PgkbException {
    sf_logger.info("starting "+this.getClass().getSimpleName()+", writing to file " + getFile());

    Session session = null;

    try(FileOutputStream out = new FileOutputStream(getFile())) {
      session = HibernateUtils.getSession();

      Row nameRow = getNextRow();
      Row descripRow = getNextRow();
      Row formatRow = getNextRow();

      descripRow.setHeightInPoints(30f);
      formatRow.setHeightInPoints(60f);

      for (Property property : Property.values()) {
        sf_logger.debug("{}: {}", property.ordinal(), property.getDisplayName());

        ExcelUtils.writeCell(descripRow, property.ordinal(), property.getDisplayName(), getHeaderStyle());
        ExcelUtils.writeCell(nameRow, property.ordinal(), property.getShortName(), getMonospaceStyle());
        ExcelUtils.writeCell(formatRow, property.ordinal(), IcpcUtils.lookupFormat(session, property), getCodeStyle());
      }

      List rez;
      if (m_project == null) {
        rez = session.createQuery("select s.subjectId from Sample s order by s.project,s.subjectId").list();
      }
      else {
        rez = session.createQuery("select s.subjectId from Sample s where s.project=:pid order by s.subjectId")
                .setInteger("pid", m_project)
                .list();
      }
      int projectId = 0;
      for (Object result : rez) {
        Sample sample = (Sample)session.get(Sample.class, (String)result);
        if (projectId != sample.getProject()) {
          sf_logger.info("writing project {}", sample.getProject());
          projectId = sample.getProject();
        }
        Row row = getNextRow();

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

      saveToOutputStream(out);
    }
    catch (Exception ex) {
      throw new PgkbException("Error writing report",ex);
    }
    finally {
      HibernateUtils.close(session);
    }
    sf_logger.info("done with {}",this.getClass().getSimpleName());
  }

  public String getSheetName() {
    return DATA_SHEET_NAME;
  }
}
