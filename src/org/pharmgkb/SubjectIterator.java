package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.sun.javafx.beans.annotations.NonNull;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pharmgkb.enums.*;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.IcpcUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 8/28/12
 */
public class SubjectIterator implements Iterator {
  private static final Logger sf_logger = Logger.getLogger(SubjectIterator.class);
  private static final Integer sf_columnNameRowIdx = 1;

  private Sheet m_sheet = null;
  private Integer m_currentRow = 3;
  private FormulaEvaluator m_formulaEvaluator = null;
  private Map<Integer,String> m_columnIdxToNameMap = Maps.newHashMap();
  private Integer m_columnCount = 0;

  public SubjectIterator(Sheet sheet) throws Exception {
    if (sheet == null) {
      throw new Exception("No sheet specified");
    }
    setSheet(sheet);

    Workbook wb = sheet.getWorkbook();
    setFormulaEvaluator(wb.getCreationHelper().createFormulaEvaluator());

    Row headerRow = getSheet().getRow(sf_columnNameRowIdx);
    setColumnCount(Integer.valueOf(headerRow.getLastCellNum()));

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("Sheet has "+sheet.getLastRowNum()+" rows");
    }
  }

  public void parseHeading(@NonNull Session session) throws Exception {
    Preconditions.checkNotNull(session);

    Query query = session.createQuery("from IcpcProperty ip where trim(ip.description)=:descrip");

    Row headerRow = getSheet().getRow(sf_columnNameRowIdx);

    if (!headerRow.getCell(0).getStringCellValue().equals("PharmGKB Subject ID")) {
      throw new Exception("Can't find proper header row at index "+sf_columnNameRowIdx);
    }

    int cellCrawlCount = 0;
    for (Cell cell : headerRow) {
      String cellContent = StringUtils.strip(cell.getStringCellValue());
      Object result = query.setParameter("descrip", cellContent).uniqueResult();

      if (result!=null) {
        IcpcProperty property = (IcpcProperty)result;
        getColumnIdxToNameMap().put(cell.getColumnIndex(), property.getName());
      }
      else {
        sf_logger.warn("No column definition found (and cannot store data) for column "+(cell.getColumnIndex()+1)+": "+cellContent);
      }
      cellCrawlCount++;
    }
    sf_logger.debug("Finished parsing header, coloumns read: "+cellCrawlCount+", columns matched: "+getColumnIdxToNameMap().size());
  }

  @Override
  public boolean hasNext() {
    boolean notPastLast = getCurrentRow()<=getSheet().getLastRowNum();

    boolean hasSubjectId = getSheet()!=null
        && getSheet().getRow(getCurrentRow())!=null
        && getSheet().getRow(getCurrentRow()).getCell(0)!=null
        && StringUtils.isNotBlank(getSheet().getRow(getCurrentRow()).getCell(0).getStringCellValue());

    return notPastLast && hasSubjectId;
  }

  @Override
  public Subject next() {
    Subject subject = new Subject();

    Row row = getSheet().getRow(getCurrentRow());

    if (sf_logger.isDebugEnabled()) {
      sf_logger.debug("row " + getCurrentRow() + " length: " + row.getLastCellNum());
    }

    try {
      copyFromRowToSubject(row, subject);
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't copy subject data for row "+getCurrentRow()+1, ex);
      subject = null;
    }

    bumpCurrentRow();
    return subject;
  }

  public Map<String,String> parseKeyValues() {
    Map<String,String> keyValues = Maps.newHashMap();
    Map<Integer,String> colIdxToKey = getColumnIdxToNameMap();

    Row row = getSheet().getRow(getCurrentRow());

    for (int colIdx=0; colIdx<getColumnCount(); colIdx++) {
      if (colIdxToKey.containsKey(colIdx)) {
        String key = colIdxToKey.get(colIdx);
        String value = ExcelUtils.getStringValue(row.getCell(colIdx), getFormulaEvaluator());

        if (StringUtils.isBlank(value)) {
          value="NA";
        }

        if (!validate(key, value)) {
          sf_logger.warn("Bad value at "+ExcelUtils.getAddress(row.getCell(colIdx)));
        }
        keyValues.put(key, value);
      }
    }

    return keyValues;
  }

  protected void copyFromRowToSubject(Row row, Subject subject) throws Exception {
    Preconditions.checkNotNull(row);
    Preconditions.checkNotNull(subject);

    for (int colIdx=0; colIdx<getColumnCount(); colIdx++) {
      String cellStringValue;
      try {
        cellStringValue = ExcelUtils.getStringValue(row.getCell(colIdx), getFormulaEvaluator());
      } catch (Exception ex) {
        sf_logger.warn("Error getting value for cell "+ExcelUtils.getAddress(row.getCell(colIdx)));
        throw ex;
      }

      if (IcpcUtils.isBlank(cellStringValue)) {
        cellStringValue = null;
        ExcelUtils.writeCell(row, colIdx, "NA");
      }

      switch(colIdx) {

        case 0:
          String subjectId = row.getCell(colIdx).getStringCellValue();
          subjectId = StringUtils.strip(StringUtils.replace(subjectId, ",", ""));
          subject.setSubjectId(subjectId);
          ExcelUtils.writeCell(row, colIdx, subjectId);
          break;

        case 1:
          subject.setGenotyping(convertToValue(cellStringValue));
          break;

        case 2:
          subject.setPhenotyping(convertToValue(cellStringValue));
          break;

        case 3:
          if (!IcpcUtils.isBlank(cellStringValue)) {
            for (String value : Splitter.on(";").trimResults().split(cellStringValue)) {
              SampleSource source = SampleSource.lookupByName(value);
              if (source == null) {
                sf_logger.warn("can't find source for : "+value);
                throw new Exception("row "+getCurrentRow()+" can't find source for : "+value);
              }
              else {
                subject.addSampleSource(SampleSource.lookupByName(value));
              }
            }
          }
          break;

        case 4:
          if (IcpcUtils.isBlank(cellStringValue)) {
            throw new Exception("Project ID must be specified");
          }
          subject.setProject(Integer.valueOf(cellStringValue));
          break;

        case 5:
          switch(cellStringValue) {
            case "1":
              subject.setGender(Gender.MALE);
              break;
            case "2":
              subject.setGender(Gender.FEMALE);
              break;
            default:
              subject.setGender(Gender.UNKNOWN);
              break;
          }
          break;

        case 6:
          subject.setRaceself(cellStringValue);
          break;

        case 7:
          subject.setRaceOMB(cellStringValue);
          break;

        case 8:
          subject.setEthnicityreported(cellStringValue);
          break;

        case 9:
          subject.setEthnicityOMB(cellStringValue);
          break;

        case 10:
          subject.setCountry(cellStringValue);
          break;

        case 11:
          subject.setAge(getNumber(row.getCell(colIdx)));
          break;

        case 12:
          // binned age ignored
          break;

        case 13:
          subject.setHeight(getNumber(row.getCell(colIdx)));
          break;

        case 14:
          subject.setWeight(getNumber(row.getCell(colIdx)));
          break;

        case 15:
          subject.setBMI(getNumber(row.getCell(colIdx)));
          break;

        case 16:
          subject.setComorbidities(cellStringValue);
          break;

        case 17:
          switch(cellStringValue) {
            case "1":
              subject.setDiabetes(DiabetesStatus.TYPE1);
              break;
            case "2":
              subject.setDiabetes(DiabetesStatus.TYPE2);
              break;
            case "0":
              subject.setDiabetes(DiabetesStatus.NONE);
              break;
            default:
              subject.setDiabetes(DiabetesStatus.UNKNOWN);
              break;
          }
          break;

        case 18:
          subject.setEverSmoked(convertToValue(cellStringValue));
          break;

        case 19:
          subject.setCurrentsmoker(convertToValue(cellStringValue));
          break;

        case 20:
          switch(cellStringValue) {
            case "0":
              subject.setAlcohol(AlcoholStatus.NONE);
              break;
            case "1":
              subject.setAlcohol(AlcoholStatus.INFREQUENT);
              break;
            case "2":
              subject.setAlcohol(AlcoholStatus.MODERATE);
              break;
            case "3":
              subject.setAlcohol(AlcoholStatus.FREQUENT);
              break;
            default:
              subject.setAlcohol(AlcoholStatus.UNKNOWN);
              break;
          }
          break;

        case 21:
          subject.setBloodPressure(convertToValue(cellStringValue));
          break;

        case 22:
          subject.setDiastolicBPMax(getNumber(row.getCell(colIdx)));
          break;

        case 23:
          subject.setDiastolicBPMedian(getNumber(row.getCell(colIdx)));
          break;

        case 24:
          subject.setSystolicBPMax(getNumber(row.getCell(colIdx)));
          break;

        case 25:
          subject.setSystolicBPMedian(getNumber(row.getCell(colIdx)));
          break;

        case 26:
          subject.setCRP(getNumber(row.getCell(colIdx)));
          break;

        case 27:
          subject.setBUN(getNumber(row.getCell(colIdx)));
          break;

        case 28:
          subject.setCreatinine(getNumber(row.getCell(colIdx)));
          break;

        case 29:
          subject.setEjectionfraction(convertToValue(cellStringValue));
          break;

        case 30:
          subject.setLeftVentricle(getNumber(row.getCell(colIdx)));
          break;

        case 31:
          subject.setPlaceboRCT(convertToValue(cellStringValue));
          break;

        case 32:
          subject.setClopidogrel(convertToValue(cellStringValue));
          break;

        case 33:
          subject.setDoseClopidogrel(getNumber(row.getCell(colIdx)));
          break;

        case 34:
          subject.setDurationClopidogrel(getNumber(row.getCell(colIdx)));
          break;

        case 35:
          subject.setAspirin(convertToValue(cellStringValue));
          break;

        case 36:
          subject.setDoseAspirin(getNumber(row.getCell(colIdx)));
          break;

        case 37:
          subject.setDurationAspirin(getNumber(row.getCell(colIdx)));
          break;

        case 38:
          subject.setStatins(convertToValue(cellStringValue));
          break;

        case 39:
          subject.setPPI(convertToValue(cellStringValue));
          break;

        case 40:
          if (IcpcUtils.isBlank(cellStringValue)) {
            break;
          }
          if (cellStringValue.contains("1")) {
            subject.addPPIname("esomeprazole");
          }
          if (cellStringValue.contains("2")) {
            subject.addPPIname("lansoprazole");
          }
          if (cellStringValue.contains("3")) {
            subject.addPPIname("omeprazole");
          }
          if (cellStringValue.contains("4")) {
            subject.addPPIname("pantoprazole");
          }
          if (cellStringValue.contains("5")) {
            subject.addPPIname("rabeprazole");
          }
          if (cellStringValue.contains("6")) {
            subject.addPPIname("other");
          }
          break;

        case 41:
          subject.setCalciumblockers(convertToValue(cellStringValue));
          break;

        case 42:
          subject.setBetablockers(convertToValue(cellStringValue));
          break;

        case 43:
          subject.setACEInh(convertToValue(cellStringValue));
          break;

        case 44:
          subject.setAnginhblockers(convertToValue(cellStringValue));
          break;

        case 45:
          subject.setEzetemib(convertToValue(cellStringValue));
          break;

        case 46:
          subject.setGlycoproteinIIaIIIbinhibitor(convertToValue(cellStringValue));
          break;

        case 47:
          subject.setActivemetabolite(getNumber(row.getCell(colIdx)));
          break;

        case 48:
          subject.setBleeding(convertToValue(cellStringValue));
          break;

        case 49:
          subject.setMajorBleeding(convertToValue(cellStringValue));
          break;

        case 50:
          subject.setMinorBleeding(convertToValue(cellStringValue));
          break;

        case 51:
          subject.setDaysMajorBleeding(ExcelUtils.getNumericValue(row.getCell(colIdx),getFormulaEvaluator()));
          break;

        case 52:
          subject.setDaysMinorBleeding(ExcelUtils.getNumericValue(row.getCell(colIdx),getFormulaEvaluator()));
          break;

        case 53:
          subject.setCVevents(convertToValue(cellStringValue));

        case 54:
          subject.setTimeMACE(getNumber(row.getCell(colIdx)));
          break;

        case 55:
          subject.setSTEMI(convertToValue(cellStringValue));
          break;

        case 56:
          subject.setTimeSTEMI(getNumber(row.getCell(colIdx)));
          break;

        case 57:
          subject.setNSTEMI(convertToValue(cellStringValue));
          break;

        case 58:
          subject.setTimeNSTEMI(getNumber(row.getCell(colIdx)));
          break;

        case 59:
          subject.setAngina(convertToValue(cellStringValue));
          break;

        case 60:
          subject.setTimeAngina(getNumber(row.getCell(colIdx)));
          break;

        case 61:
          subject.setREVASC(convertToValue(cellStringValue));
          break;

        case 62:
          subject.setTimeREVASC(getNumber(row.getCell(colIdx)));
          break;

        case 63:
          subject.setStroke(convertToValue(cellStringValue));
          break;

        case 64:
          subject.setTimestroke(getNumber(row.getCell(colIdx)));
          break;

        case 65:
          subject.setOtherischemic(convertToValue(cellStringValue));
          break;

        case 66:
          subject.setCongestiveHeartFailure(convertToValue(cellStringValue));
          break;

        case 67:
          subject.setTimeheartFailure(getNumber(row.getCell(colIdx)));
          break;

        case 68:
          subject.setMechanicalValveReplacement(convertToValue(cellStringValue));
          break;

        case 69:
          subject.setTimeMechValve(getNumber(row.getCell(colIdx)));
          break;

        case 70:
          subject.setTissueValveReplacement(convertToValue(cellStringValue));
          break;

        case 71:
          subject.setTimeMechValve(getNumber(row.getCell(colIdx)));
          break;

        case 72:
          subject.setStentthromb(convertToValue(cellStringValue));
          break;

        case 73:
          subject.setTimestent(getNumber(row.getCell(colIdx)));
          break;

        case 74:
          subject.setAllcausemortality(convertToValue(cellStringValue));
          break;

        case 75:
          subject.setTimemortality(getNumber(row.getCell(colIdx)));
          break;

        case 76:
          subject.setCardiovasculardeath(convertToValue(cellStringValue));
          break;

        case 77:
          subject.setTimedeath(getNumber(row.getCell(colIdx)));
          break;

        case 78:
          subject.setAtrialfibrillation(convertToValue(cellStringValue));
          break;

        case 79:
          subject.setTimeAF(getNumber(row.getCell(colIdx)));
          break;

        case 80:
          subject.setLeftventricularhypertrophy(convertToValue(cellStringValue));
          break;

        case 81:
          subject.setTimevenHypertrophy(getNumber(row.getCell(colIdx)));
          break;

        case 82:
          subject.setPeripheralvasculardisease(convertToValue(cellStringValue));
          break;

        case 83:
          subject.setTimePeriVascular(getNumber(row.getCell(colIdx)));
          break;

        case 84:
          subject.setDurationfollowupclinicaloutcomes(getNumber(row.getCell(colIdx)));
          break;

        case 85:
          subject.setBloodCell(convertToValue(cellStringValue));
          break;

        case 86:
          subject.setWhitecellcount(getNumber(row.getCell(colIdx)));
          break;

        case 87:
          subject.setRedcellcount(getNumber(row.getCell(colIdx)));
          break;

        case 88:
          subject.setPlateletcount(getNumber(row.getCell(colIdx)));
          break;

        case 89:
          subject.setMeanplateletvolume(getNumber(row.getCell(colIdx)));
          break;

        case 90:
          subject.setHematocrit(getNumber(row.getCell(colIdx)));
          break;

        case 91:
          subject.setChol(convertToValue(cellStringValue));
          break;

        case 92:
          subject.setLDL(getNumber(row.getCell(colIdx)));
          break;

        case 93:
          subject.setHDL(getNumber(row.getCell(colIdx)));
          break;

        case 94:
          subject.setTotalCholesterol(getNumber(row.getCell(colIdx)));
          break;

        case 95:
          subject.setTriglycerides(getNumber(row.getCell(colIdx)));
          break;

        case 96:
          subject.setPlatAggrPheno(cellStringValue);
          break;

        case 97:
          subject.setInstrument(cellStringValue);
          break;

        case 98:
          subject.setInterassayvariation(getNumber(row.getCell(colIdx)));
          break;

        case 99:
          subject.setBloodcollectiontype(cellStringValue);
          break;

        case 100:
          subject.setSampletype(cellStringValue);
          break;

        case 101:
          subject.setVerifyNowbase(convertToValue(cellStringValue));
          break;

        case 102:
          subject.setVerifyNowpostloading(convertToValue(cellStringValue));
          break;

        case 103:
          subject.setVerifyNowwhileonclopidogrel(convertToValue(cellStringValue));
          break;

        case 104:
          subject.setOpticalPlateletAggregometry(getNumber(row.getCell(colIdx)));
          break;

        case 105:
          subject.setPreclopidogrelplateletaggregometrybase(convertToValue(cellStringValue));
          break;

        case 106:
          subject.setPostclopidogrelplateletaggregometry(convertToValue(cellStringValue));
          break;

        case 107:
          subject.setAggregometryagonist(cellStringValue);
          break;

        case 108:
          subject.setADP(cellStringValue);
          break;

        case 109:
          subject.setArachadonicacid(cellStringValue);
          break;

        case 110:
          subject.setCollagen(cellStringValue);
          break;

        case 111:
          subject.setPlateletaggregometrymethod(cellStringValue);
          break;

        case 112:
          subject.setClopidogrelloadingdose(getNumber(row.getCell(colIdx)));
          break;

        case 200:
          if (StringUtils.isNotBlank(cellStringValue)) {
            for (String genotype : Splitter.on("/").split(cellStringValue)) {
              subject.addCyp2c19genotype(genotype);
            }
          }
          break;

        case 201:
          subject.setRs4244285(cellStringValue);
          break;

        case 202:
          subject.setRs4986893(cellStringValue);
          break;

        case 203:
          subject.setRs28399504(cellStringValue);
          break;

        case 204:
          subject.setRs56337013(cellStringValue);
          break;

        case 205:
          subject.setRs72552267(cellStringValue);
          break;

        case 206:
          subject.setRs72558186(cellStringValue);
          break;

        case 207:
          subject.setRs41291556(cellStringValue);
          break;

        case 208:
          subject.setRs6413438(cellStringValue);
          break;

        case 209:
          subject.setRs12248560(cellStringValue);
          break;

        case 210:
          subject.setRs662(cellStringValue);
          break;

        case 211:
          subject.setRs854560(cellStringValue);
          break;

        case 212:
          subject.setRs1045642(cellStringValue);
          break;

        case 213:
          subject.setOthergenotypes(cellStringValue);

        default:
          break;
      }
    }
  }

  protected boolean validate(String key, String value) {
    boolean valid = true;

    if (IcpcUtils.isBlank(value)) {
      return valid;
    }

    String strippedValue = StringUtils.stripToNull(value);

    switch (key) {
      // subject ID column is special
      case "Subject_ID":
        return (strippedValue.startsWith("PA") && strippedValue.length()>2);

        // columns that must be integers
      case "Project":
        try {
          Integer.valueOf(strippedValue);
        }
        catch (Exception ex) {
          valid = false;
        }
        break;

      // enum columns
      case "Alcohol":
        valid = (AlcoholStatus.lookupByName(strippedValue) != null);
        break;
      case "Diabetes":
        valid = (DiabetesStatus.lookupByName(strippedValue) != null);
        break;
      case "Gender":
        valid = (Gender.lookupByName(strippedValue) != null);
        break;
      case "Sample_Source":
        for (String token : Splitter.on(";").split(strippedValue)) {
          valid = valid && (SampleSource.lookupByName(StringUtils.strip(token))!=null);
        }
        break;

      // columns that must be floats
      case "Age":
      case "Height":
      case "Weight":
      case "BMI":
      case "Diastolic_BP_Max":
      case "Diastolic_BP_Median":
      case "Systolic_BP_Max":
      case "Systolic_BP_Median":
      case "CRP":
      case "BUN":
      case "Left_Ventricle":
      case "Right_Ventricle":
      case "Dose_Clopidogrel_aspirin":
      case "Duration_Clopidogrel":
      case "Duration_Aspirin":
      case "Duration_therapy":
      case "Active_metabolite":
      case "Days_MajorBleeding":
      case "Days_MinorBleeding":
      case "Num_bleeding":
      case "PFA_mean_EPI_Collagen_closure_Baseline":
      case "PFA_mean_ADP_Collagen_closure_Baseline":
      case "PFA_mean_EPI_Collagen_closure_Post":
      case "PFA_mean_ADP_Collagen_closure_Post":
      case "PFA_mean_EPI_Collagen_closure_Standard":
      case "PFA_mean_ADP_Collagen_closure_Standard":
      case "Verify_Now_baseline_Base":
      case "Verify_Now_baseline_PRU":
      case "Verify_Now_baseline_percentinhibition":
      case "Verify_Now_post_Base":
      case "Verify_Now_post_PRU":
      case "Verify_Now_post_percentinhibition":
      case "Verify_Now_on_clopidogrel_Base":
      case "Verify_Now_on_clopidogrel_PRU":
      case "Verify_Now_on_clopidogrel_percentinhibition":
      case "PAP_8_baseline_max_ADP_2 ":
      case "PAP_8_baseline_max_ADP_5":
      case "PAP_8_baseline_max_ADP_10":
      case "PAP_8_baseline_max_ADP_20":
      case "PAP_8_baseline_max_collagen_1":
      case "PAP_8_baseline_max_collagen_2":
      case "PAP_8_baseline_max_collagen_10":
      case "PAP_8_baseline_max_collagen_6":
      case "PAP_8_baseline_max_epi":
      case "PAP_8_baseline_max_aa":
      case "PAP_8_baseline_lag_collagen_1":
      case "PAP_8_baseline_lag_collagen_2":
      case "PAP_8_baseline_lag_collagen_5":
      case "PAP_8_baseline_lag_collagen_10":
      case "PAP_8_post_max_ADP_2 ":
      case "PAP_8_post_max_ADP_5":
      case "PAP_8_post_max_ADP_10":
      case "PAP_8_post_max_ADP_20":
      case "PAP_8_post_max_collagen_1":
      case "PAP_8_post_max_collagen_2":
      case "PAP_8_post_max_collagen_5":
      case "PAP_8_post_max_collagen_10":
      case "PAP_8_post_max_epi_perc":
      case "PAP_8_post_max_aa_perc":
      case "PAP_8_post_lag_collagen_1":
      case "PAP_8_post_lag_collagen_2":
      case "PAP_8_post_lag_collagen_5":
      case "PAP_8_post_lag_collagen_10":
      case "PAP_8_standard_max_ADP_2":
      case "PAP_8_standard_max_ADP_5":
      case "PAP_8_standard_max_ADP_10":
      case "PAP_8_standard_max_ADP_20":
      case "PAP_8_standard_max_collagen_1":
      case "PAP_8_standard_max_collagen_2":
      case "PAP_8_standard_max_collagen_5":
      case "PAP_8_standard_max_collagen_10":
      case "PAP_8_standard_max_epi_pct":
      case "PAP_8_standard_max_aa_pct":
      case "PAP_8_standard_lag_collagen_1":
      case "PAP_8_standard_lag_collagen_2":
      case "5PAP_8_standard_lag_collagen_5":
      case "PAP_8_standard_lag_collagen_10":
      case "Chronolog_baseline_max_ADP_5":
      case "Chronolog_baseline_max_ADP_20":
      case "Chronolog_baseline_max_aa":
      case "Chronolog_baseline_max_collagen1":
      case "Chronolog_baseline_lag_ADP_5":
      case "Chronolog_baseline_lag_ADP_20":
      case "Chronolog_baseline_lag_aa":
      case "Chronolog_baseline_lag_collagen1":
      case "Chronolog_loading_max_ADP_5":
      case "Chronolog_loading_max_ADP_20":
      case "Chronolog_loading_max_aa":
      case "Chronolog_loading_max_collagen1":
      case "Chronolog_loading_lag_ADP_5":
      case "Chronolog_loading_lag_ADP_20":
      case "Chronolog_loading_lag_aa":
      case "Chronolog_loading_lag_collagen1":
      case "Chronolog_standard_max_ADP_5":
      case "Chronolog_standard_max_ADP_20":
      case "Chronolog_standard_max_aa":
      case "Chronolog_standard_max_collagen1":
      case "Chronolog_standard_lag_ADP_5":
      case "Chronolog_standard_lag_ADP_20":
      case "Chronolog_standard_lag_aa":
      case "Chronolog_standard_lag_collagen1":
      case "VASP":
      case "Duration_followup_clinical_outcomes":
      case "Time_STEMI":
      case "Time_NSTEMI":
      case "Time_Angina":
      case "Time_REVASC":
      case "Time_stroke":
      case "Time_heartFailure":
      case "Time_MechValve":
      case "Time_tissValve":
      case "Time_stent":
      case "Time_mortality":
      case "Time_death":
      case "Time_venHypertrophy":
      case "Time_PeriVascular":
      case "Time_AF":
      case "Time_Loading_PFA":
      case "Time_loading_VerifyNow":
      case "Time_loading_PAP8":
      case "Time_loading_Chronolog":
      case "Clopidogrel_loading_dose":
      case "White_cell_count":
      case "Red_cell_count":
      case "Platelet_count":
      case "Abs_white_on_plavix":
      case "Red_on_plavix":
      case "Platelet_on_plavix":
      case "MeanPlateletVol_on_plavix":
      case "Hematocrit_on_plavix":
      case "Mean_platelet_volume":
      case "Hematocrit ":
      case "LDL":
      case "HDL":
      case "Total_Cholesterol":
      case "Triglycerides":
      case "Inter_assay_variation":
      case "Intra_assay_variation":
      case "Optical_Platelet_Aggregometry":
      case "Time_MACE":
        try {
          Float.valueOf(strippedValue);
        }
        catch (Exception ex) {
          valid = false;
        }
        break;

      // columns with no/yes as 0/1
      case "Genotyping":
      case "Phenotyping":
      case "Ejection_fraction":
      case "Clopidogrel":
      case "Aspirn":
      case "Clopidogrel_alone":
      case "ADP":
      case "Arachadonic_acid":
      case "Collagen":
      case "Verify_Now_base":
      case "Verify_Now_post_loading":
      case "Verify_Now_while_on_clopidogrel":
      case "Pre_clopidogrel_platelet_aggregometry_base":
      case "Post_clopidogrel_platelet_aggregometry":
        return (strippedValue!=null
            && (StringUtils.strip(value).equals("0") || StringUtils.strip(value).equals("1")));

      // columns with no/yes/unknown as 0/1/99
      case "Ever_Smoked":
      case "Current_smoker":
      case "Blood_Pressure":
      case "placebo_RCT":
      case "Aspirin_Less_100":
      case "Other_medications":
      case "Statins":
      case "PPI ":
      case "Calcium_blockers":
      case "Beta_blockers":
      case "ACE_Inh":
      case "Ang_inh_blockers":
      case "Ezetemib":
      case "Glycoprotein_IIaIIIb_inhibitor":
      case "CV_events":
      case "Bleeding":
      case "Major_Bleeding":
      case "Minor_Bleeding":
      case "STEMI":
      case "NSTEMI":
      case "Other_ischemic":
      case "MI ":
      case "Stroke":
      case "All_cause_mortality":
      case "Cardiovascular_death":
      case "Angina":
      case "Left_ventricular_hypertrophy":
      case "Peripheral_vascular_disease":
      case "Atrial_fibrillation":
      case "REVASC":
      case "Congestive_Heart_Failure":
      case "Tissue_Valve_Replacement":
      case "Blood_Cell":
      case "Chol":
        return (strippedValue!=null
            && (StringUtils.strip(value).equals("0") || StringUtils.strip(value).equals("1") || StringUtils.strip(value).equals("99")));

      // columns with left/right/no/unknown as 0/1/2/99
      case "Stent_thromb":
      case "Mechanical_Valve_Replacement":
        return (strippedValue!=null
            && (StringUtils.strip(value).equals("0") || StringUtils.strip(value).equals("1") || StringUtils.strip(value).equals("2") || StringUtils.strip(value).equals("99")));

      // columns that are stored as strings and can skip validation
      case "Creatinine":
        return true;

        // no validation
      default:
        if (sf_logger.isDebugEnabled()) {
          sf_logger.debug("no validation for "+key);
        }
    }

    return valid;
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

  protected Value convertToValue(String string) {
    Value value = Value.Unknown;

    if (StringUtils.isNotBlank(string)) {
      value = Value.lookupByName(string);
      if (value == null) {
        value = Value.Unknown;
      }
    }
    return value;
  }

  public FormulaEvaluator getFormulaEvaluator() {
    return m_formulaEvaluator;
  }

  public void setFormulaEvaluator(FormulaEvaluator formulaEvaluator) {
    m_formulaEvaluator = formulaEvaluator;
  }

  public Double getNumber(Cell cell) {
    return ExcelUtils.getNumericValue(cell, getFormulaEvaluator());
  }

  public Map<Integer, String> getColumnIdxToNameMap() {
    return m_columnIdxToNameMap;
  }

  public Integer getColumnCount() {
    return m_columnCount;
  }

  public void setColumnCount(Integer columnCount) {
    m_columnCount = columnCount;
  }
}
