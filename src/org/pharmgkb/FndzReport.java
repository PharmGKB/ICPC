package org.pharmgkb;

import com.google.common.collect.ImmutableList;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.IcpcUtils;

import java.io.File;
import java.util.List;

/**
 * A dump of properties for a request made on 12/16/2015. This excludes site 4 and limits to the GWAS samples.
 *
 * @author Ryan Whaley
 */
public class FndzReport extends AbstractReport {
  private static final int sf_defaultColumnWidth = 20;
  private static final String sf_filename = "fndz.report.xlsx";
  private static final String sf_sheetName = "Subjects";

  private static final List<Property> sf_columns = ImmutableList.of(
      Property.SUBJECT_ID,
      Property.RIKEN_ID,
      Property.AGE,
      Property.GENDER,
      Property.PROJECT,
      Property.ASPIRN,
      Property.DOSE_ASPIRIN,
      Property.DURATION_ASPIRIN,
      Property.CLOPIDOGREL,
      Property.DOSE_CLOPIDOGREL,
      Property.DURATION_CLOPIDOGREL,
      Property.STEMI,
      Property.TIME_STEMI,
      Property.NSTEMI,
      Property.TIME_NSTEMI,
      Property.STROKE,
      Property.TIME_STROKE,
      Property.PERIPHERAL_VASCULAR_DISEASE,
      Property.MI_DURING_FOLLOWUP,
      Property.TTF_MI,
      Property.PERI_ART_DISEASE_BASE,
      Property.PLAT_AGGR_PHENO,
      Property.INSTRUMENT,
      Property.INTER_ASSAY_VARIATION,
      Property.BLOOD_COLLECTION_TYPE,
      Property.SAMPLE_TYPE,
      Property.VERIFY_NOW_BASE,
      Property.VERIFY_NOW_POST_LOADING,
      Property.OPTICAL_PLATELET_AGGREGOMETRY,
      Property.PRE_CLOPIDOGREL_PLATELET_AGGREGOMETRY_BASE,
      Property.POST_CLOPIDOGREL_PLATELET_AGGREGOMETRY,
      Property.AGGREGOMETRY_AGONIST,
      Property.ADP,
      Property.ARACHADONIC_ACID,
      Property.COLLAGEN,
      Property.PLATELET_AGGREGOMETRY_METHOD,
      Property.CLOPIDOGREL_LOADING_DOSE,
      Property.PFA_MEAN_EPI_COLLAGEN_CLOSURE_BASELINE,
      Property.PFA_MEAN_ADP_COLLAGEN_CLOSURE_BASELINE,
      Property.PFA_MEAN_EPI_COLLAGEN_CLOSURE_POST,
      Property.PFA_MEAN_ADP_COLLAGEN_CLOSURE_POST,
      Property.TIME_LOADING_PFA,
      Property.PFA_MEAN_EPI_COLLAGEN_CLOSURE_STANDARD,
      Property.PFA_MEAN_ADP_COLLAGEN_CLOSURE_STANDARD,
      Property.VERIFY_NOW_BASELINE_BASE,
      Property.VERIFY_NOW_BASELINE_PRU,
      Property.VERIFY_NOW_BASELINE_PERCENTINHIBITION,
      Property.VERIFY_NOW_POST_BASE,
      Property.VERIFY_NOW_POST_PRU,
      Property.VERIFY_NOW_POST_PERCENTINHIBITION,
      Property.TIME_LOADING_VERIFYNOW,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_BASE,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_PRU,
      Property.VERIFY_NOW_ON_CLOPIDOGREL_PERCENTINHIBITION,
      Property.PAP_8_BASELINE_MAX_ADP_2,
      Property.PAP_8_BASELINE_MAX_ADP_5,
      Property.PAP_8_BASELINE_MAX_ADP_10,
      Property.PAP_8_BASELINE_MAX_ADP_20,
      Property.PAP_8_BASELINE_MAX_COLLAGEN_1,
      Property.PAP_8_BASELINE_MAX_COLLAGEN_2,
      Property.PAP_8_BASELINE_MAX_COLLAGEN_10,
      Property.PAP_8_BASELINE_MAX_COLLAGEN_6,
      Property.PAP_8_BASELINE_MAX_EPI,
      Property.PAP_8_BASELINE_MAX_AA,
      Property.PAP_8_BASELINE_LAG_COLLAGEN_1,
      Property.PAP_8_BASELINE_LAG_COLLAGEN_2,
      Property.PAP_8_BASELINE_LAG_COLLAGEN_5,
      Property.PAP_8_BASELINE_LAG_COLLAGEN_10,
      Property.PAP_8_POST_MAX_ADP_2,
      Property.PAP_8_POST_MAX_ADP_5,
      Property.PAP_8_POST_MAX_ADP_10,
      Property.PAP_8_POST_MAX_ADP_20,
      Property.PAP_8_POST_MAX_COLLAGEN_1,
      Property.PAP_8_POST_MAX_COLLAGEN_2,
      Property.PAP_8_POST_MAX_COLLAGEN_5,
      Property.PAP_8_POST_MAX_COLLAGEN_10,
      Property.PAP_8_POST_MAX_EPI_PERC,
      Property.PAP_8_POST_MAX_AA_PERC,
      Property.PAP_8_POST_LAG_COLLAGEN_1,
      Property.PAP_8_POST_LAG_COLLAGEN_2,
      Property.PAP_8_POST_LAG_COLLAGEN_5,
      Property.PAP_8_POST_LAG_COLLAGEN_10,
      Property.TIME_LOADING_PAP8,
      Property.PAP_8_STANDARD_MAX_ADP_2,
      Property.PAP_8_STANDARD_MAX_ADP_5,
      Property.PAP_8_STANDARD_MAX_ADP_10,
      Property.PAP_8_STANDARD_MAX_ADP_20,
      Property.PAP_8_STANDARD_MAX_COLLAGEN_1,
      Property.PAP_8_STANDARD_MAX_COLLAGEN_2,
      Property.PAP_8_STANDARD_MAX_COLLAGEN_5,
      Property.PAP_8_STANDARD_MAX_COLLAGEN_10,
      Property.PAP_8_STANDARD_MAX_EPI_PCT,
      Property.PAP_8_STANDARD_MAX_AA_PCT,
      Property.PAP_8_STANDARD_LAG_COLLAGEN_1,
      Property.PAP_8_STANDARD_LAG_COLLAGEN_2,
      Property.PAP_8_STANDARD_LAG_COLLAGEN_5,
      Property.PAP_8_STANDARD_LAG_COLLAGEN_10,
      Property.CHRONOLOG_BASELINE_MAX_ADP_5,
      Property.CHRONOLOG_BASELINE_MAX_ADP_20,
      Property.CHRONOLOG_BASELINE_MAX_AA,
      Property.CHRONOLOG_BASELINE_MAX_COLLAGEN1,
      Property.CHRONOLOG_BASELINE_LAG_ADP_5,
      Property.CHRONOLOG_BASELINE_LAG_ADP_20,
      Property.CHRONOLOG_BASELINE_LAG_AA,
      Property.CHRONOLOG_BASELINE_LAG_COLLAGEN1,
      Property.CHRONOLOG_LOADING_MAX_ADP_5,
      Property.CHRONOLOG_LOADING_MAX_ADP_20,
      Property.CHRONOLOG_LOADING_MAX_AA,
      Property.CHRONOLOG_LOADING_MAX_COLLAGEN1,
      Property.CHRONOLOG_LOADING_LAG_ADP_5,
      Property.CHRONOLOG_LOADING_LAG_ADP_20,
      Property.CHRONOLOG_LOADING_LAG_AA,
      Property.CHRONOLOG_LOADING_LAG_COLLAGEN1,
      Property.TIME_LOADING_CHRONOLOG,
      Property.CHRONOLOG_STANDARD_MAX_ADP_5,
      Property.CHRONOLOG_STANDARD_MAX_ADP_20,
      Property.CHRONOLOG_STANDARD_MAX_AA,
      Property.CHRONOLOG_STANDARD_MAX_COLLAGEN1,
      Property.CHRONOLOG_STANDARD_LAG_ADP_5,
      Property.CHRONOLOG_STANDARD_LAG_ADP_20,
      Property.CHRONOLOG_STANDARD_LAG_AA,
      Property.CHRONOLOG_STANDARD_LAG_COLLAGEN1,
      Property.VASP,
      Property.VASP_LD,
      Property.VASP_MD,
      Property.CHRONOLOG_BASELINE_LTA_MAX,
      Property.CHRONOLOG_BASELINE_LTA_FINAL,
      Property.CONE_ADP_SURFACE_PCT,
      Property.CONE_ADP_SURFACE_SIZE,
      Property.MULTIPLATE_ADP_TEST,
      Property.MULTIPLATE_AA_TEST,
      Property.CARAT_2MICRO_AGGMAX,
      Property.CARAT_5MICRO_POST_MAX,
      Property.CARAT_5MICRO_POST_LATE,
      Property.CARAT_5MICRO_MAIN_MAX,
      Property.CARAT_5MICRO_MAIN_LATE,
      Property.PAP4_BASE_MAX_ADP_2M,
      Property.PAP4_BASE_MAX_ADP_5M,
      Property.PAP4_BASE_MAX_ADP_10M,
      Property.PAP4_BASE_MAX_ADP_20M,
      Property.PAP4_BASE_MAX_COL_1,
      Property.PAP4_BASE_MAX_COL_2,
      Property.PAP4_BASE_MAX_COL_5,
      Property.PAP4_BASE_MAX_COL_10,
      Property.PAP4_BASE_MAX_EPI,
      Property.PAP4_BASE_MAX_ARACHADONIC,
      Property.PAP4_BASE_LAG_COL_1,
      Property.PAP4_BASE_LAG_COL_2,
      Property.PAP4_BASE_LAG_COL_5,
      Property.PAP4_BASE_LAB_COL_10,
      Property.PAP4_PLAVIX_ADP_2M,
      Property.PAP4_PLAVIX_ADP_5M,
      Property.PAP4_PLAVIX_ADP_10M,
      Property.PAP4_PLAVIX_ADP_20M,
      Property.PAP4_PLAVIX_MAX_COL_1,
      Property.PAP4_PLAVIX_MAX_COL_2,
      Property.PAP4_PLAVIX_MAX_COL_5,
      Property.PAP4_PLAVIX_MAX_COL_10,
      Property.PAP4_PLAVIX_MAX_EPI,
      Property.PAP4_PLAVIX_MAX_ARACHADONIC,
      Property.PAP4_PLAVIX_LAG_COL_1,
      Property.PAP4_PLAVIX_LAG_COL_2,
      Property.PAP4_PLAVIX_LAG_COL_5,
      Property.PAP4_PLAVIX_LAG_COL_10,
      Property.TIME_LOADING_TO_PAP4,
      Property.PAP4_MAINT_ADP_2M,
      Property.PAP4_MAINT_ADP_5M,
      Property.PAP4_MAINT_ADP_10M,
      Property.PAP4_MAINT_ADP_20M,
      Property.PAP4_MAINT_MAX_COL_1,
      Property.PAP4_MAINT_MAX_COL_2,
      Property.PAP4_MAINT_MAX_COL_5,
      Property.PAP4_MAINT_MAX_COL_10,
      Property.PAP4_MAINT_MAX_EPI,
      Property.PAP4_MAINT_MAX_ARACHADONIC,
      Property.PAP4_MAINT_LAG_COL_1,
      Property.PAP4_MAINT_LAG_COL_2,
      Property.PAP4_MAINT_LAG_COL_5,
      Property.PAP4_MAINT_LAG_COL_10,
      Property.ADDITION_PHENO,
      Property.PHENO_RAW,
      Property.PHENO_STD
  );

  public FndzReport(File dir) {
    super(sf_defaultColumnWidth);
    setFile(new File(dir, sf_filename));
  }

  public boolean includeSample(Sample sample) {
    String rikenId = sample.getProperties().get(Property.RIKEN_ID);
    return !IcpcUtils.isBlank(rikenId) && sample.getProject()!=4;
  }

  @Override
  public void generate() throws PgkbException {
    writeSampleProperties(sf_columns);
  }

  @Override
  public String getSheetName() {
    return sf_sheetName;
  }
}
