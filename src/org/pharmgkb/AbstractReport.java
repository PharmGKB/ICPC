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

  public AbstractReport(int defaultWidth) {
    m_workbook = new XSSFWorkbook();

    m_sheet = m_workbook.createSheet(getSheetName());
    m_sheet.setDefaultColumnWidth(defaultWidth);

    setStyles();
  }

  public abstract void generate() throws PgkbException;

  public Row getNextRow() {
    return m_sheet.createRow(rowIdx++);
  }

  public void saveToOutputStream(OutputStream out) throws IOException {
    m_workbook.write(out);
  }

  public boolean includeSample(Sample sample) {
    return sample != null;
  }

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


  public File getFile() {
    return m_file;
  }

  public void setFile(File file) {
    m_file = file;
  }

  public CellStyle getHeaderStyle() {
    return m_headerStyle;
  }

  public CellStyle getCodeStyle() {
    return m_codeStyle;
  }

  public CellStyle getMonospaceStyle() {
    return m_monospaceStyle;
  }

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

  public abstract String getSheetName();
}
