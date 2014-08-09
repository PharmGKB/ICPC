package org.pharmgkb.util;

import com.sun.istack.internal.Nullable;
import com.sun.javafx.beans.annotations.NonNull;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * A collection of utility methods for working wth Excel files in Apache POI
 *
 * @author Ryan Whaley
 */
public class ExcelUtils {
  private static Logger sf_logger = LoggerFactory.getLogger(ExcelUtils.class);

  /**
   * Gets the human-readable address of <code>cell</code> in the {@link Workbook}. For example: A1, B32, AA5
   *
   * @param cell an excel {@link Cell}
   * @return a readable String for the address
   */
  public static String getAddress(@NonNull Cell cell) {
    return CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1);
  }

  /**
   * Write a text cell to the given row at the given index. Convenience method when no {@link CellStyle} needs to be
   * specified.
   *
   * @param row a {@link Row} from an excel {@link Workbook}
   * @param idx the index of the cell to write
   * @param value the String value to write
   */
  public static void writeCell(Row row, int idx, String value) {
    writeCell(row, idx, value, null);
  }

  /**
   * Write a text cell to the given row at the given index.
   *
   * @param row a {@link Row} from an excel {@link Workbook}
   * @param idx the index of the cell to write
   * @param value the String value to write
   * @param highlight the {@link CellStyle} to use, optional
   */
  public static void writeCell(Row row, int idx, String value, @Nullable CellStyle highlight) {
    // first normalizeValue all the important arguments
    if (value == null || row == null || idx<0) {
      // don't do anything if they're missing
      return;
    }

    Cell cell = row.getCell(idx);

    if (cell == null) {
      row.createCell(idx).setCellValue(value);
    }
    else {
      try {
        cell.setCellType(Cell.CELL_TYPE_STRING);

        if (!value.equals(cell.getStringCellValue())) {
          sf_logger.debug("Changed value: " + getAddress(cell) + " = " + cell.getStringCellValue() + " -> " + value);
          if (highlight != null) {
            cell.setCellStyle(highlight);
          }
        }
      }
      catch (Exception ex) {
        sf_logger.error("Error writing to cell "+ExcelUtils.getAddress(row.getCell(idx)));
        throw ex;
      }
      cell.setCellValue(value);
    }
  }

  /**
   * Write a numeric cell to the given row at the given index.
   *
   * @param row a {@link Row} from an excel {@link Workbook}
   * @param idx the index of the cell to write
   * @param value the <code>double</code> value to write
   * @param highlight the {@link CellStyle} to use, optional
   */
  public static void writeCell(Row row, int idx, double value, @Nullable CellStyle highlight) {
    Cell cell = row.getCell(idx);
    if (cell == null) {
      row.createCell(idx, Cell.CELL_TYPE_NUMERIC).setCellValue(value);
    }
    else {
      if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
        Double existingValue = cell.getNumericCellValue();
        if (value != existingValue) {
          sf_logger.debug("Changed value: " + CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1) + " = " + cell.getNumericCellValue() + " -> " + value);
          if (highlight != null) {
            cell.setCellStyle(highlight);
          }
        }
      }
      else {
        String existingValue = cell.getStringCellValue();

        sf_logger.debug("Changed value: " + CellReference.convertNumToColString(cell.getColumnIndex()) + (cell.getRowIndex() + 1) + " = " + existingValue + " -> " + value);

        row.removeCell(cell);
        cell = row.createCell(idx,Cell.CELL_TYPE_NUMERIC);
        if (highlight != null) {
          cell.setCellStyle(highlight);
        }
      }

      cell.setCellValue(value);
    }
  }

  /**
   * Converts numbers that include exponents into a regular number.
   *
   * @param number original number
   * @return reformatted number
   **/
  private static String formatNumber(double number) {

    String numString = Double.toString(number);
    int idx = numString.indexOf((int)'E');
    if (idx == -1) {
      // lop off trailing .0
      if (numString.endsWith(".0")) {
        numString = numString.substring(0, numString.length() - 2);
      }
      return numString;
    }

    int exponent = Integer.parseInt(numString.substring(idx + 1));
    int precision = idx - 1;
    if (exponent > 0 && exponent == precision) {
      precision++;
    }
    BigDecimal bd = new BigDecimal(number, new MathContext(precision));
    return bd.toPlainString();
  }

  /**
   * Gets the string value of a cell.
   *
   * @param cell the cell to get the string value of
   * @return the string value of the specified cell
   */
  public static String getStringValue(Cell cell, FormulaEvaluator formulaEvaluator) {

    if (cell != null) {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
          cell.setCellType(Cell.CELL_TYPE_NUMERIC);
          return formatNumber(cell.getNumericCellValue());
        case Cell.CELL_TYPE_BOOLEAN:
          return Boolean.toString(cell.getBooleanCellValue());
        case Cell.CELL_TYPE_FORMULA:
          return getFormulaResultString(formulaEvaluator.evaluate(cell));
        default:
          return StringUtils.stripToNull(cell.getRichStringCellValue().getString());
      }
    }
    return null;
  }

  /**
   * Gets the <code>String</code> value of a formula result
   * @param cell a CellValue that came from a formula result
   * @return a String value
   */
  private static String getFormulaResultString(CellValue cell) {

    if (cell != null) {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
          return formatNumber(cell.getNumberValue());
        case Cell.CELL_TYPE_BOOLEAN:
          return Boolean.toString(cell.getBooleanValue());
        default:
          return StringUtils.stripToNull(cell.getStringValue());
      }
    }
    return null;
  }
}
