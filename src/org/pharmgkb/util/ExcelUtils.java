package org.pharmgkb.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: Jul 28, 2010
 * Time: 8:35:06 AM
 */
public class ExcelUtils {
  private static Logger sf_logger = Logger.getLogger(ExcelUtils.class);

  public static String getAddress(Cell cell) {
    StringBuilder sb = new StringBuilder();
    sb.append(CellReference.convertNumToColString(cell.getColumnIndex()));
    sb.append(cell.getRowIndex()+1);
    return sb.toString();
  }

  public static void writeCell(Row row, int idx, String value) {
    writeCell(row, idx, value, null);
  }

  public static void writeCell(Row row, int idx, String value, CellStyle highlight) {
    // first validate all the important arguments
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
          StringBuilder sb = new StringBuilder();
          sb.append("Changed value: ")
              .append(getAddress(cell))
              .append(" = ")
              .append(cell.getStringCellValue())
              .append(" -> ")
              .append(value);
          sf_logger.info(sb.toString());
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

  public static void writeCell(Row row, int idx, float value, CellStyle highlight) {
    Cell cell = row.getCell(idx);
    if (cell == null) {
      row.createCell(idx).setCellValue(value);
    }
    else {
      if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
        Double existingValue = cell.getNumericCellValue();
        if (value != existingValue.floatValue()) {
          StringBuilder sb = new StringBuilder();
          sb.append("Changed value: ")
              .append(CellReference.convertNumToColString(cell.getColumnIndex()))
              .append(cell.getRowIndex()+1)
              .append(" = ")
              .append(cell.getNumericCellValue())
              .append(" -> ")
              .append(value);
          sf_logger.info(sb.toString());
          if (highlight != null) {
            cell.setCellStyle(highlight);
          }
        }
      }
      else {
        String existingValue = cell.getStringCellValue();

        StringBuilder sb = new StringBuilder();
        sb.append("Changed value: ")
            .append(CellReference.convertNumToColString(cell.getColumnIndex()))
            .append(cell.getRowIndex()+1)
            .append(" = ")
            .append(existingValue)
            .append(" -> ")
            .append(value);
        sf_logger.info(sb.toString());

        row.removeCell(cell);
        row.createCell(idx).setCellType(Cell.CELL_TYPE_NUMERIC);
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
          return getStringValue(formulaEvaluator.evaluate(cell));
        default:
          return StringUtils.stripToNull(cell.getRichStringCellValue().getString());
      }
    }
    return null;
  }

  public static String getStringValue(CellValue cell) {

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

  public static Double getNumberValue(CellValue cell) {

    if (cell != null && cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
      return cell.getNumberValue();
    }
    else if (cell != null && cell.getCellType()==Cell.CELL_TYPE_STRING) {
      if (StringUtils.isNotBlank(cell.getStringValue())) {
        return Double.valueOf(cell.getStringValue());
      }
    }
    return null;
  }

  public static Double getNumericValue(Cell cell, FormulaEvaluator formulaEvaluator) {
    Double number = null;

    if (cell != null && cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
      number = cell.getNumericCellValue();
    }
    else if (cell != null && cell.getCellType()==Cell.CELL_TYPE_FORMULA) {
      number = getNumberValue(formulaEvaluator.evaluate(cell));
    }

    return number;
  }

}
