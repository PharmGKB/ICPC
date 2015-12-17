package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.IcpcUtils;

import java.io.File;
import java.util.List;

/**
 * A report for a request made on 12/16/2015. This includes only samples included in the GWAS analysis and is a small
 * subset of properties in the full dataset.
 *
 * @author Ryan Whaley
 */
public class GwasReport extends AbstractReport {
  private static final int sf_defaultColumnWidth = 20;
  private static final String sf_filename = "gwas.report.xlsx";
  private static final String sf_sheetName = "Subjects";
  private static final List<Property> sf_columns = ImmutableList.of(
      Property.SUBJECT_ID,
      Property.RIKEN_ID,
      Property.RACE_SELF,
      Property.RACE_OMB,
      Property.DIABETES,
      Property.EVER_SMOKED,
      Property.CURRENT_SMOKER,
      Property.CREATININE,
      Property.CREATININE_CAT,
      Property.CLOPIDOGREL,
      Property.DOSE_CLOPIDOGREL,
      Property.DURATION_CLOPIDOGREL,
      Property.ASPIRN,
      Property.DOSE_ASPIRIN,
      Property.DURATION_ASPIRIN,
      Property.STATINS,
      Property.PPI,
      Property.PPI_NAME,
      Property.CALCIUM_BLOCKERS,
      Property.BETA_BLOCKERS,
      Property.ACE_INH,
      Property.ANG_INH_BLOCKERS,
      Property.EZETEMIB,
      Property.GLYCOPROTEIN_IIAIIIB_INHIBITOR,
      Property.BMI,
      Property.AGE,
      Property.GENDER,
      Property.TTF_ACS,
      Property.ACS_DURING_FOLLOWUP,
      Property.ANGINA,
      Property.TIME_ANGINA,
      Property.PCI_INFORMATION,
      Property.CLINICAL_SETTING,
      Property.INDICATION_CLOPIDOGREL,
      Property.VERIFY_NOW_POST_BASE,
      Property.VERIFY_NOW_POST_PRU,
      Property.VERIFY_NOW_POST_PERCENTINHIBITION,
      Property.TIME_LOADING_VERIFYNOW,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_BASE,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_PRU,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_PERCENTINHIBITION
  );

  public GwasReport(File dir) {
    super(sf_defaultColumnWidth);
    Preconditions.checkNotNull(dir);
    setFile(new File(dir, sf_filename));
  }

  public boolean includeSample(Sample sample) {
    String rikenId = sample.getProperties().get(Property.RIKEN_ID);
    return !IcpcUtils.isBlank(rikenId);
  }

  @Override
  public void generate() throws PgkbException {
    writeSampleProperties(sf_columns);
  }

  public String getSheetName() {
    return sf_sheetName;
  }
}
