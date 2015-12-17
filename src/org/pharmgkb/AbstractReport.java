package org.pharmgkb;

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
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Extend this class to generate an Excel file that lists properties for {@link Sample} objects.
 *
 * @author Ryan Whaley
 */
public abstract class AbstractReport {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private File m_file;
  private Workbook m_workbook;
  private Sheet m_sheet;
  private int rowIdx = 0;
  private CellStyle m_headerStyle;
  private CellStyle m_codeStyle;
  private CellStyle m_monospaceStyle;

  /**
   * Abstract constructor. Sets up objects for working with Excel file.
   * @param defaultWidth the default width of columns to be displayed in the sheet
   */
  public AbstractReport(int defaultWidth) {
    m_workbook = new XSSFWorkbook();

    m_sheet = m_workbook.createSheet(getSheetName());
    m_sheet.setDefaultColumnWidth(defaultWidth);

    setStyles();
  }

  /**
   * Implement this method to acutally generate the Excel sheet
   * @throws PgkbException can occur from file IO
   */
  public abstract void generate() throws PgkbException;

  /**
   * Gets the next row of the Excel sheet. Keeps an internal counter to know which row is being written
   * @return an Excel {@link Row}
   */
  public Row getNextRow() {
    return m_sheet.createRow(rowIdx++);
  }

  /**
   * Writes the Excel sheet out to the given {@link OutputStream}.
   * @param out an {@link OutputStream} to write to
   * @throws IOException
   */
  public void saveToOutputStream(OutputStream out) throws IOException {
    m_workbook.write(out);
  }

  /**
   * Should the supplied sample be written to the Excel file? true = write, false = don't write.
   *
   * This is a default implementaiton that just makes sure sample is not null. You should override this if you have more
   * checks to make.
   * @param sample a {@link Sample} object to test for inclusion
   * @return true = include, false = don't include
   */
  public boolean includeSample(Sample sample) {
    return sample != null;
  }

  /**
   * This is a common method for running through all {@link Sample} objects in the DB, filtering using the
   * <code>includeSample</code> method, and then writing the given {@link List} of {@link Property} values to the
   * specified filename.
   *
   * This will have header rows for describing the {@link Property} enums being written.
   *
   * @param properties a {@link List} of {@link Property} values to write as columns to the Excel sheet
   * @throws PgkbException
   */
  public void writeSampleProperties(List<Property> properties) throws PgkbException {
    Session session = null;
    try(FileOutputStream out = new FileOutputStream(getFile())) {
      session = HibernateUtils.getSession();

      Row codeRow = getNextRow();
      Row titleRow = getNextRow();
      Row formatRow = getNextRow();

      for (int i=0; i<properties.size(); i++) {
        Property property = properties.get(i);

        ExcelUtils.writeCell(titleRow, i, property.getDisplayName(), getHeaderStyle());
        ExcelUtils.writeCell(codeRow, i, property.getShortName(), getMonospaceStyle());
        ExcelUtils.writeCell(formatRow, i, IcpcUtils.lookupFormat(session, property), getCodeStyle());
      }

      //noinspection unchecked
      List<String> rez = session.createQuery("select s.subjectId from Sample s order by s.subjectId").list();
      for (String sampleId : rez) {
        Sample sample = (Sample)session.get(Sample.class, sampleId);

        if (includeSample(sample)) {
          Row sampleRow = getNextRow();
          for (int i = 0; i < properties.size(); i++) {
            ExcelUtils.writeCell(sampleRow, i, sample.getProperties().get(properties.get(i)));
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


  /**
   * Gets the file name to write to
   */
  public File getFile() {
    return m_file;
  }

  /**
   * Sets the file name to write to
   */
  public void setFile(File file) {
    m_file = file;
  }

  /**
   * Gets the {@link CellStyle} to use for header cells
   */
  public CellStyle getHeaderStyle() {
    return m_headerStyle;
  }

  /**
   * Gets the {@link CellStyle} to use for code cells
   */
  public CellStyle getCodeStyle() {
    return m_codeStyle;
  }

  /**
   * Gets the {@link CellStyle} to use for monospace text
   */
  public CellStyle getMonospaceStyle() {
    return m_monospaceStyle;
  }

  /**
   * initializes all the different {@link CellStyle} objects to use for display in the Excel sheet
   */
  private void setStyles() {
    m_headerStyle = m_workbook.createCellStyle();
    m_headerStyle.setWrapText(true);
    Font bold = m_workbook.createFont();
    bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
    m_headerStyle.setFont(bold);

    m_codeStyle = m_workbook.createCellStyle();
    Font italic = m_workbook.createFont();
    italic.setItalic(true);
    m_codeStyle.setFont(italic);

    m_monospaceStyle = m_workbook.createCellStyle();
    Font mono = m_workbook.createFont();
    mono.setFontName("Courier");
    m_monospaceStyle.setFont(mono);
  }

  /**
   * Gets the default name of the Excel sheet that's being written to
   */
  public abstract String getSheetName();
}
