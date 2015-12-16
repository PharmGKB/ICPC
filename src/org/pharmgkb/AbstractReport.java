package org.pharmgkb;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pharmgkb.exception.PgkbException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Ryan Whaley
 */
public abstract class AbstractReport {

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
