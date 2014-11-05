package org.pharmgkb;

import com.google.common.base.Preconditions;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Abstract class for making a parser class for an Excel file
 *
 * @author Ryan Whaley
 */
public abstract class SupplementalParser {
  protected static final Integer sf_idxSampleId = 0;
  private Workbook m_workbook = null;
  private FormulaEvaluator m_evaluator = null;

  /**
   * Public constructor
   *
   * @param file the Excel file holding data to parse
   * @throws Exception
   */
  public SupplementalParser(File file) throws Exception {
    Preconditions.checkNotNull(file);
    Preconditions.checkArgument(file.exists());
    Preconditions.checkArgument(file.isFile());

    try (InputStream is = new FileInputStream(file)) {
      setWorkbook(WorkbookFactory.create(is));
      m_evaluator = getWorkbook().getCreationHelper().createFormulaEvaluator();
    }
  }

  public abstract void parse() throws Exception;

  /**
   * Get the {@link Workbook} object this parser is crawling
   * @return the {@link Workbook} to parse
   */
  public Workbook getWorkbook() {
    return m_workbook;
  }

  /**
   * Set the {@link Workbook} object this parser is crawling
   * @param workbook a {@link Workbook} of DNA data
   */
  public void setWorkbook(Workbook workbook) {
    m_workbook = workbook;
  }

  public FormulaEvaluator getEvaluator() {
    return m_evaluator;
  }
}
