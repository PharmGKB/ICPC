package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
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
  private static final Map<Property,Property> sf_eventTimeMap = Maps.newHashMap();
  static {
    sf_eventTimeMap.put(Property.MAJOR_BLEEDING, Property.DAYS_MAJORBLEEDING);
    sf_eventTimeMap.put(Property.MINOR_BLEEDING, Property.DAYS_MINORBLEEDING);
    sf_eventTimeMap.put(Property.STEMI, Property.TIME_STEMI);
    sf_eventTimeMap.put(Property.NSTEMI, Property.TIME_NSTEMI);
    sf_eventTimeMap.put(Property.ANGINA, Property.TIME_ANGINA);
    sf_eventTimeMap.put(Property.REVASC, Property.TIME_REVASC);
    sf_eventTimeMap.put(Property.STROKE, Property.TIME_STROKE);
    sf_eventTimeMap.put(Property.CONGESTIVE_HEART_FAILURE, Property.TIME_HEARTFAILURE);
    sf_eventTimeMap.put(Property.MECHANICAL_VALVE_REPLACEMENT, Property.TIME_MECHVALVE);
    sf_eventTimeMap.put(Property.TISSUE_VALVE_REPLACEMENT, Property.TIME_TISSVALVE);
    sf_eventTimeMap.put(Property.STENT_THROMB, Property.TIME_STENT);
    sf_eventTimeMap.put(Property.ALL_CAUSE_MORTALITY, Property.TIME_MORTALITY);
    sf_eventTimeMap.put(Property.CARDIOVASCULAR_DEATH, Property.TIME_DEATH);
    sf_eventTimeMap.put(Property.LEFT_VENTRICULAR_HYPERTROPHY, Property.TIME_VENHYPERTROPHY);
    sf_eventTimeMap.put(Property.PERIPHERAL_VASCULAR_DISEASE, Property.TIME_PERIVASCULAR);
    sf_eventTimeMap.put(Property.ATRIAL_FIBRILLATION, Property.TIME_AF);
    sf_eventTimeMap.put(Property.CV_EVENTS, Property.TIME_TO_MACE);
  }

  private Sheet m_sheet = null;
  private Integer m_currentRow = 3;
  private FormulaEvaluator m_formulaEvaluator = null;
  private Map<Integer,Property> m_columnIdxToNameMap = Maps.newHashMap();

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

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("Sheet has "+sheet.getLastRowNum()+" rows");
    }
  }

  public void parseHeading(Session session) throws Exception {
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

    Row row = getSheet().getRow(getCurrentRow());

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("row " + getCurrentRow() + " length: " + row.getLastCellNum());
    }

    try {
      for (Integer colIdx : getColumnIdxToNameMap().keySet()) {
        Property property = getColumnIdxToNameMap().get(colIdx);
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

        try {
          sample.addProperty(property, normalizedValue);
        }
        catch (Exception ex) {
          sf_logger.error("error with value in {}", ExcelUtils.getAddress(row.getCell(colIdx)));
        }

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
  protected static void postProcess(Sample sample) {
    // BMI calculation
    sample.addProperty(Property.BMI, IcpcUtils.calculateBmi(sample));

    // Creatinine fix
    sample.addProperty(Property.CREATININE, IcpcUtils.convertCreatinine(sample));

    // Creatinine category
    sample.addProperty(Property.CREATININE_CAT, IcpcUtils.calculateCreatinineCat(sample));

    // set race properly using all available info
    sample.addProperty(Property.RACE_OMB, sample.calculateRace());

    // if subject currently smokes, then they have smoked
    String currentSmoker = sample.getProperties().get(Property.CURRENT_SMOKER);
    if (currentSmoker != null && currentSmoker.equals(Value.Yes.getShortName())) {
      sample.addProperty(Property.EVER_SMOKED, Value.Yes.getShortName());
    }

    // if LVEF has a value, then it's available
    String lvef = sample.getProperties().get(Property.LVEF);
    sample.addProperty(Property.LVEF_CATEGORY, lvef);
    if (!IcpcUtils.isBlank(lvef) && !lvef.equals("0")) {
      sample.addProperty(Property.LVEF_AVAIL, Value.Yes.getShortName());
    }
    String lvefAvail = sample.getProperties().get(Property.LVEF_AVAIL);
    if (IcpcUtils.isBlank(lvefAvail)) {
      sample.addProperty(Property.LVEF_AVAIL, Value.No.getShortName());
    }

    // fix time to event properties, put in the value for maximum followup when answer is No
    String maxDays = sample.getProperties().get(Property.DURATION_FOLLOWUP_CLINICAL_OUTCOMES);
    for (Property event : sf_eventTimeMap.keySet()) {
      String eventStatus = sample.getProperties().get(event);
      if (!IcpcUtils.isBlank(eventStatus) && Value.lookupByName(eventStatus) == Value.No) {
        sample.addProperty(sf_eventTimeMap.get(event), maxDays);
      }
    }

    // impute MI value from STEMI and NSTEMI values
    String nstemi = sample.getProperties().get(Property.NSTEMI);
    String stemi = sample.getProperties().get(Property.STEMI);
    if (!IcpcUtils.isBlank(stemi) && stemi.equals(Value.Yes.getShortName())) {
      sample.addProperty(Property.MI_DURING_FOLLOWUP, Value.Yes.getShortName());
    }
    else if (!IcpcUtils.isBlank(nstemi) && nstemi.equals(Value.Yes.getShortName())) {
      sample.addProperty(Property.MI_DURING_FOLLOWUP, Value.Yes.getShortName());
    }
    else if (!IcpcUtils.isBlank(stemi) && !IcpcUtils.isBlank(nstemi) && stemi.equals(Value.No.getShortName()) && nstemi.equals(Value.No.getShortName())) {
      sample.addProperty(Property.MI_DURING_FOLLOWUP, Value.No.getShortName());
    }

    // fix the problem with caucasiens
    String raceSelf = sample.getProperties().get(Property.RACE_SELF);
    if (!IcpcUtils.isBlank(raceSelf) && raceSelf.equals("caucasien")) {
      sample.addProperty(Property.RACE_SELF, "caucasian");
    }

    // white counts from projects need to be multiplied by 1k
    String whiteCell = sample.getProperties().get(Property.WHITE_CELL_COUNT);
    if (!IcpcUtils.validateNumberFloor(whiteCell, 1000)) {
      sf_logger.warn(Property.WHITE_CELL_COUNT.getDisplayName() + " is low for " + sample.getSubjectId() + ", updated");
      Double whiteCellCount = Double.valueOf(whiteCell)*1000;
      whiteCell = String.valueOf(whiteCellCount);
      sample.addProperty(Property.WHITE_CELL_COUNT, whiteCell);
    }

    // red counts from projects need to be multiplied by 1m
    String redCell = sample.getProperties().get(Property.RED_CELL_COUNT);
    if (!IcpcUtils.validateNumberFloor(redCell,1_000_000)) {
      sf_logger.warn(Property.RED_CELL_COUNT.getDisplayName() + " is low for "+sample.getSubjectId() + ", updated");
      double rbcAdjusted = Double.valueOf(redCell);
      if (rbcAdjusted>0 && rbcAdjusted<1_000) {
        rbcAdjusted = rbcAdjusted*1_000_000;
      } else if (rbcAdjusted>=1_000 && rbcAdjusted<1_000_000) {
        rbcAdjusted = rbcAdjusted*1_000;
      }
      redCell = String.valueOf(rbcAdjusted);
      sample.addProperty(Property.RED_CELL_COUNT, redCell);
    }

    // platelet counts from projects need to be multiplied by 1k
    String platelet = sample.getProperties().get(Property.PLATELET_COUNT);
    if (!IcpcUtils.validateNumberFloor(platelet,1000)) {
      sf_logger.warn(Property.PLATELET_COUNT.getDisplayName() + " is low for "+sample.getSubjectId() + ", updated");
      Double plateletCount = Double.valueOf(platelet)*1000;
      platelet = String.valueOf(plateletCount);
      sample.addProperty(Property.PLATELET_COUNT, platelet);
    }

    // correct type "0" stent thrombosis to "NA"
    String stentType = sample.getProperties().get(Property.TYPE_STENT_THROMB);
    if (!IcpcUtils.isBlank(stentType) && stentType.equals("0")) {
      sample.addProperty(Property.TYPE_STENT_THROMB, IcpcUtils.NA);
    }
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
}
