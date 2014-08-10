package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.sun.javafx.beans.annotations.NonNull;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.enums.*;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.IcpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NonUniqueResultException;
import java.util.*;

/**
 * This class will take a Sheet and do the parsing to generate {@link org.pharmgkb.model.Sample} objects. This will also
 * parse the header rows of the Sheet to ensure all properties in the file are represented in the DB. All the cells in
 * each row are also checked for validity depending on rules based on the header property.
 *
 * @author Ryan Whaley
 */
public class SubjectIterator implements Iterator {
  private static final Logger sf_logger = LoggerFactory.getLogger(SubjectIterator.class);
  private static final Integer sf_columnNameRowIdx = 1;

  private Sheet m_sheet = null;
  private Integer m_currentRow = 3;
  private FormulaEvaluator m_formulaEvaluator = null;
  private Map<Integer,Property> m_columnIdxToNameMap = Maps.newHashMap();
  private Integer m_columnCount = 0;

  /**
   * Constructor for this {@link org.pharmgkb.SubjectIterator}. Expects to take in a Sheet from an Excel workbook with
   * the second row being a header row of property names.
   * @param sheet a Sheet from an Excel Workbook with {@link org.pharmgkb.model.Sample} data in it
   * @throws Exception can occur if an invalid <code>sheet</code> is specified
   */
  public SubjectIterator(Sheet sheet) throws Exception {
    if (sheet == null) {
      throw new Exception("No sheet specified");
    }
    setSheet(sheet);

    Workbook wb = sheet.getWorkbook();
    setFormulaEvaluator(wb.getCreationHelper().createFormulaEvaluator());

    Row headerRow = getSheet().getRow(sf_columnNameRowIdx);
    setColumnCount((int)headerRow.getLastCellNum());

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("Sheet has "+sheet.getLastRowNum()+" rows");
    }
  }

  public void parseHeading(@NonNull Session session) throws Exception {
    Preconditions.checkNotNull(session);
    Map<String,String> unmappedColumnMap = Maps.newTreeMap();

    Row headerRow = getSheet().getRow(sf_columnNameRowIdx);

    if (!headerRow.getCell(0).getStringCellValue().equals("PharmGKB Subject ID")) {
      throw new Exception("Can't find proper header row at index "+sf_columnNameRowIdx);
    }

    int cellCrawlCount = 0;
    for (Cell cell : headerRow) {
      String cellContent = StringUtils.strip(cell.getStringCellValue());
      if (!IcpcUtils.isBlank(cellContent)) {
        try {
          Property property = Property.lookupByName(cellContent);

          if (property!=null) {
            getColumnIdxToNameMap().put(cell.getColumnIndex(), property);
          }
          else {
            unmappedColumnMap.put(ExcelUtils.getAddress(cell), cellContent);
          }
        } catch (NonUniqueResultException ex) {
          throw new PgkbException("More than one property with description: " + cellContent, ex);
        }
      }
      cellCrawlCount++;
    }

    if (unmappedColumnMap.size()>0) {
      throw new PgkbException("No column definitions found for:\n"+unmappedColumnMap);
    }

    sf_logger.debug("Finished parsing header, coloumns read: "+cellCrawlCount+", columns matched: "+getColumnIdxToNameMap().size());
  }

  @Override
  public boolean hasNext() {
    if (getSheet()==null) {
      return false;
    }

    boolean notPastLast = getCurrentRow()<=getSheet().getLastRowNum();

    boolean hasSubjectId = getSheet().getRow(getCurrentRow())!=null
        && getSheet().getRow(getCurrentRow()).getCell(0)!=null
        && StringUtils.isNotBlank(getSheet().getRow(getCurrentRow()).getCell(0).getStringCellValue());

    return notPastLast && hasSubjectId;
  }

  @Override
  public Sample next() {
    Sample sample = new Sample();

    Map<Integer,Property> colIdxToProperty = getColumnIdxToNameMap();
    Row row = getSheet().getRow(getCurrentRow());

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("row " + getCurrentRow() + " length: " + row.getLastCellNum());
    }

    try {
      for (int colIdx=0; colIdx<getColumnCount(); colIdx++) {
        Property property = colIdxToProperty.get(colIdx);
        String value = ExcelUtils.getStringValue(row.getCell(colIdx), getFormulaEvaluator());

        if (IcpcUtils.isBlank(value)) {
          sample.addProperty(property, IcpcUtils.NA);
          continue;
        }

        String normalizedValue = value;
        if (property.validate(value)) {
          normalizedValue = property.normalize(value);
        }
        else {
          sf_logger.warn("[project "+sample.getProject()+"] Bad value for "+property.getShortName()+" in "+ExcelUtils.getAddress(row.getCell(colIdx))+": "+value);
        }

        sample.addProperty(property, normalizedValue);

        // some properties get set in the Smaple object itself
        switch (property) {
          case SUBJECT_ID:
            sample.setSubjectId(normalizedValue);
            break;
          case GENOTYPING:
            sample.setGenotyping(Value.lookupByName(normalizedValue));
            break;
          case PHENOTYPING:
            sample.setPhenotyping(Value.lookupByName(normalizedValue));
            break;
          case SAMPLE_SOURCE:
            sample.setSampleSource((SampleSource.parseList(normalizedValue)));
            break;
          case PROJECT:
            sample.setProject(Integer.valueOf(normalizedValue));
            break;
          case GENDER:
            sample.setGender(Gender.lookupByName(normalizedValue));
            break;
          case RACE_SELF:
            sample.setRaceself(normalizedValue);
            break;
          case RACE_OMB:
            sample.setRaceOMB(normalizedValue);
            break;
          case ETHNICITY_REPORTED:
            sample.setEthnicityreported(normalizedValue);
            break;
          case ETHNICITY_OMB:
            sample.setEthnicityOMB(normalizedValue);
            break;
          case COUNTRY:
            sample.setCountry(normalizedValue);
            break;
          case AGE:
            if (IcpcUtils.isBlank(normalizedValue)) {
              sf_logger.warn("no age specified for "+sample.getSubjectId());
            }
            sample.setAge(Double.valueOf(normalizedValue));
            break;
        }
      }
      postProcess(sample);
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't copy subject data for row "+(getCurrentRow()+1), ex);
      sample = null;
    }

    bumpCurrentRow();
    return sample;
  }

  /**
   * Processing that can only be done after all the properties for a sample have been assigned.
   * @param sample a Sample record with all properties set
   */
  protected void postProcess(Sample sample) {
    // BMI calculation
    sample.addProperty(Property.BMI, IcpcUtils.calculateBmi(sample));

    // Creatinine fix
    sample.addProperty(Property.CREATININE, IcpcUtils.convertCreatinine(sample));

    // Creatinine category
    sample.addProperty(Property.CREATININE_CAT, IcpcUtils.calculateCreatinineCat(sample));
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("remove() not implemented for SubjectIterator");
  }

  public Sheet getSheet() {
    return m_sheet;
  }

  public void setSheet(Sheet sheet) {
    m_sheet = sheet;
  }

  protected Integer getCurrentRow() {
    return m_currentRow;
  }

  protected void bumpCurrentRow() {
    m_currentRow++;
  }

  public FormulaEvaluator getFormulaEvaluator() {
    return m_formulaEvaluator;
  }

  public void setFormulaEvaluator(FormulaEvaluator formulaEvaluator) {
    m_formulaEvaluator = formulaEvaluator;
  }

  public Map<Integer, Property> getColumnIdxToNameMap() {
    return m_columnIdxToNameMap;
  }

  public Integer getColumnCount() {
    return m_columnCount;
  }

  public void setColumnCount(Integer columnCount) {
    m_columnCount = columnCount;
  }
}
