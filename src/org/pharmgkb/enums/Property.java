package org.pharmgkb.enums;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;
import org.pharmgkb.util.IcpcUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ryan Whaley
 */
public enum Property implements ExtendedEnum {
  SUBJECT_ID        (0, "Subject_ID", "PharmGKB Subject ID", Pattern.compile("(PA\\d+),?")),
  GENOTYPING        (1, "Genotyping", "Genetic Sample Available for GWAS & Genotyping QC (> 2 µg)", IcpcUtils.VALIDATOR_BINARY_REQ),
  PHENOTYPING       (2, "Phenotyping", "Tissue Sample(s) Available for additional Phenotyping or QC", IcpcUtils.VALIDATOR_BINARY_REQ),
  SAMPLE_SOURCE     (3, "Sample_Source", "Tissue Sample Source  Available for Phenotyping and/or QC", SampleSource.validationPattern()),
  PROJECT           (4, "Project", "Project Site", Pattern.compile("\\d+")),
  GENDER            (5, "Gender", "Gender", IcpcUtils.makeEnumValidator(Gender.class)),
  RACE_SELF         (6, "Race_self", "Race (self-reported)", null),
  RACE_OMB          (7, "Race_OMB", "Race (OMB)", Race.validationPattern()),
  ETHNICITY_REPORTED(8, "Ethnicity_reported", "Ethnicity (Reported)  (not required for minimal dataset)", null),
  ETHNICITY_OMB     (9, "Ethnicity_OMB", "Ethnicity (OMB)", null),
  COUNTRY           (10, "Country", "Country of Origin", null),
  AGE               (11, "Age", "Age at Time of Consent", IcpcUtils.VALIDATOR_NUMBER),
  HEIGHT            (12, "Height", "Height (cm)", IcpcUtils.VALIDATOR_NUMBER),
  WEIGHT            (13, "Weight", "Weight (kg)", IcpcUtils.VALIDATOR_NUMBER),
  BMI               (14, "BMI", "BMI (not required for minimal dataset)", IcpcUtils.VALIDATOR_NUMBER),
  COMORBIDITIES     (15, "Comorbidities", "Comorbidities (Disease States)", null),
  DIABETES          (16, "Diabetes", "Diabetes status", IcpcUtils.makeEnumValidator(DiabetesStatus.class)),
  EVER_SMOKED       (17, "Ever_Smoked", "Ever Smoked", IcpcUtils.VALIDATOR_BINARY),
  CURRENT_SMOKER    (18, "Current_smoker", "Current smoker", IcpcUtils.VALIDATOR_BINARY),
  ALCOHOL           (19, "Alcohol", "Alcohol intake", IcpcUtils.makeEnumValidator(AlcoholStatus.class)),
  BLOOD_PRESSURE    (20, "Blood_Pressure", "Blood Pressure/Hypertension (required for minimal dataset)", IcpcUtils.VALIDATOR_BINARY),
  DIASTOLIC_BP_MAX  (21, "Diastolic_BP_Max", "Diastolic BP_Max", IcpcUtils.VALIDATOR_NUMBER),
  DIASTOLIC_BP_MEDIAN(22, "Diastolic_BP_Median", "Diastolic BP_Median", IcpcUtils.VALIDATOR_NUMBER),
  SYSTOLIC_BP_MAX   (23, "Systolic_BP_Max", "Systolic BP_Max", IcpcUtils.VALIDATOR_NUMBER),
  SYSTOLIC_BP_MEDIAN(24, "Systolic_BP_Median", "Systolic BP_Median", IcpcUtils.VALIDATOR_NUMBER),
  CRP               (25, "CRP", "hs-CRP (mg/L)", IcpcUtils.VALIDATOR_NUMBER),
  BUN               (26, "BUN", "BUN (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  CREATININE        (27, "Creatinine", "Creatinine level (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  CREATININE_CAT    (265, "Creatinine_Category", "Creatinine Category", IcpcUtils.VALIDATOR_BINARY),
  LVEF_AVAIL        (28, "Ejection_fraction", "Availability of Ejection fraction from the placebo arm of RCTs for analysis of effect modification (optional)", IcpcUtils.VALIDATOR_BINARY),
  LVEF              (29, "Left_Ventricle", "Left Ventricle Ejection Fraction", IcpcUtils.VALIDATOR_NUMBER),
  PLACEBO_RCT       (30, "placebo_RCT", "Availability of platelet aggregation or clinical outcomes from the placebo arm of RCTs for analysis of effect modification (not part of minimal dataset)", IcpcUtils.VALIDATOR_BINARY),
  CLOPIDOGREL       (31, "Clopidogrel", "Clopidogrel therapy?", IcpcUtils.VALIDATOR_BINARY_REQ),
  DOSE_CLOPIDOGREL  (32, "Dose_Clopidogrel", "Maintenance Dose of Clopidogrel (mg/day)", IcpcUtils.VALIDATOR_NUMBER),
  DURATION_CLOPIDOGREL(33, "Duration_Clopidogrel", "Duration of Clopidogrel therapy at followup", IcpcUtils.VALIDATOR_NUMBER),
  ASPIRN            (34, "Aspirn", "Aspirin therapy?", IcpcUtils.VALIDATOR_BINARY_REQ),
  DOSE_ASPIRIN      (35, "Dose_Aspirin", "Therapeutic Dose of Aspirin (mg/day)", IcpcUtils.VALIDATOR_NUMBER),
  DURATION_ASPIRIN  (36, "Duration_Aspirin", "Duration of Aspirin therapy at followup", IcpcUtils.VALIDATOR_NUMBER),
  STATINS           (37, "Statins", "Statins", IcpcUtils.VALIDATOR_BINARY),
  PPI               (38, "PPI", "PPIs", IcpcUtils.VALIDATOR_BINARY),
  PPI_NAME          (39, "PPI_name", "Please provide the names of PPIs used", null),
  CALCIUM_BLOCKERS  (40, "Calcium_blockers", "Calcium Channel Blockers", IcpcUtils.VALIDATOR_BINARY),
  BETA_BLOCKERS     (41, "Beta_blockers", "Beta Blockers", IcpcUtils.VALIDATOR_BINARY),
  ACE_INH           (42, "ACE_Inh", "ACE Inhibitors", IcpcUtils.VALIDATOR_BINARY),
  ANG_INH_BLOCKERS  (43, "Ang_inh_blockers", "Angiotensin receptor blockers", IcpcUtils.VALIDATOR_BINARY),
  EZETEMIB          (44, "Ezetemib", "Ezetemib", IcpcUtils.VALIDATOR_BINARY),
  GLYCOPROTEIN_IIAIIIB_INHIBITOR(45, "Glycoprotein_IIaIIIb_inhibitor", "Glycoprotein IIa IIIb inhibitor use (required)", IcpcUtils.VALIDATOR_BINARY),
  ACTIVE_METABOLITE (46, "Active_metabolite", "Clopidogrel active metabolite levels (optional)", IcpcUtils.VALIDATOR_NUMBER),
  CV_EVENTS         (47, "CV_events", "Cardiovascular events during followup", IcpcUtils.VALIDATOR_BINARY),
  TIME_TO_MACE      (263, "time_to_mace", "Time to the first  major adverse cardiac events (MACE) during followup", IcpcUtils.VALIDATOR_NUMBER),
  BLEEDING          (49, "Bleeding", "Bleeding/Other adverse events reporting", IcpcUtils.VALIDATOR_BINARY),
  MAJOR_BLEEDING    (50, "Major_Bleeding", "Major Bleeding Events", IcpcUtils.VALIDATOR_BINARY),
  DAYS_MAJORBLEEDING(52, "Days_MajorBleeding", "Time to the first major bleeding event during follow up", IcpcUtils.VALIDATOR_NUMBER),
  MINOR_BLEEDING    (51, "Minor_Bleeding", "Minor Bleeding Events", IcpcUtils.VALIDATOR_BINARY),
  DAYS_MINORBLEEDING(53, "Days_MinorBleeding", "Time to the first minor bleeding event during follow up", IcpcUtils.VALIDATOR_NUMBER),
  STEMI             (54, "STEMI", "STEMI during follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_STEMI        (55, "Time_STEMI", "Time to the first STEMI", IcpcUtils.VALIDATOR_NUMBER),
  NSTEMI            (56, "NSTEMI", "NSTEMI during follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_NSTEMI       (57, "Time_NSTEMI", "Time to the first NSTEMI", IcpcUtils.VALIDATOR_NUMBER),
  ANGINA            (58, "Angina", "UNSTABLE ANGINA during follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_ANGINA       (59, "Time_Angina", "Time to the first UNSTABLE ANGINA during follow up", IcpcUtils.VALIDATOR_NUMBER),
  REVASC            (60, "REVASC", "REVASCULARIZATION (REVASC)  during follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_REVASC       (61, "Time_REVASC", "Time to the first REVASCULARIZATION (REVASC)  during follow up", IcpcUtils.VALIDATOR_NUMBER),
  STROKE            (62, "Stroke", "ischemic CVA (stroke) during follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_STROKE       (63, "Time_stroke", "Time to the first ischemic CVA during follow up", IcpcUtils.VALIDATOR_NUMBER),
  OTHER_ISCHEMIC    (64, "Other_ischemic", "Other ischemic events during follow up", IcpcUtils.VALIDATOR_BINARY),
  CONGESTIVE_HEART_FAILURE(65, "Congestive_Heart_Failure", "Congestive Heart Failure and/or Cardiomyopathy duing follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_HEARTFAILURE (66, "Time_heartFailure", "Time to the first Congestive Heart Failure and/or Cardiomyopathy duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  MECHANICAL_VALVE_REPLACEMENT(67, "Mechanical_Valve_Replacement", "Mechanical Valve Replacement duing follow up", IcpcUtils.VALIDATOR_THREE),
  TIME_MECHVALVE    (68, "Time_MechValve", "Time to the first mechanical Valve Replacement duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  TISSUE_VALVE_REPLACEMENT(69, "Tissue_Valve_Replacement", "Tissue Valve Replacement duing follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_TISSVALVE    (70, "Time_tissValve", "Time to the first tissue Valve Replacement duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  STENT_THROMB      (71, "Stent_thromb", "Stent Thrombosis duing follow up", IcpcUtils.VALIDATOR_THREE),
  TYPE_STENT_THROMB (318, "type_stent_thromb", "type of stent thrombosis", Pattern.compile("([0123]|NA)")),
  TIME_STENT        (72, "Time_stent", "Time to the first Stent Thrombosis duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  STENT_TYPE        (320, "stent_type","stent type", IcpcUtils.VALIDATOR_TERTIARY),
  ALL_CAUSE_MORTALITY(73, "All_cause_mortality", "All cause mortality duing follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_MORTALITY    (74, "Time_mortality", "Time to the first All cause mortality duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  CARDIOVASCULAR_DEATH(75, "Cardiovascular_death", "Cardiovascular death (well adjudicated by committee independent of physician in charge) duing follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_DEATH        (76, "Time_death", "Time to the first Cardiovascular death duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  LEFT_VENTRICULAR_HYPERTROPHY(77, "Left_ventricular_hypertrophy", "Left ventricular hypertrophy duing follow up (optional)", IcpcUtils.VALIDATOR_BINARY),
  TIME_VENHYPERTROPHY(78, "Time_venHypertrophy", "Time to Left ventricular hypertrophy duing follow up (optional)", IcpcUtils.VALIDATOR_NUMBER),
  PERIPHERAL_VASCULAR_DISEASE(79, "Peripheral_vascular_disease", "Peripheral vascular disease duing follow up (optional)", IcpcUtils.VALIDATOR_BINARY),
  TIME_PERIVASCULAR (80, "Time_PeriVascular", "Time to Peripheral vascular disease (optional)", IcpcUtils.VALIDATOR_NUMBER),
  ATRIAL_FIBRILLATION(81, "Atrial_fibrillation", "Atrial fibrillation duing follow up", IcpcUtils.VALIDATOR_BINARY),
  TIME_AF           (82, "Time_AF", "Time to the first Atrial fibrillation duing follow up", IcpcUtils.VALIDATOR_NUMBER),
  DURATION_FOLLOWUP_CLINICAL_OUTCOMES(83, "Duration_followup_clinical_outcomes", "Duration of followup for clinical outcomes", IcpcUtils.VALIDATOR_NUMBER),
  BLOOD_CELL        (84, "Blood_Cell", "Blood cell count (white and red) (Optional)", IcpcUtils.VALIDATOR_BINARY),
  WHITE_CELL_COUNT  (85, "White_cell_count", "Absolute White cell count (cells/µL)", IcpcUtils.VALIDATOR_NUMBER),
  RED_CELL_COUNT    (86, "Red_cell_count", "Red cell count (cells/µL)", IcpcUtils.VALIDATOR_NUMBER),
  PLATELET_COUNT    (87, "Platelet_count", "Platelet count (cells/µL)", IcpcUtils.VALIDATOR_NUMBER),
  MEAN_PLATELET_VOLUME(88, "Mean_platelet_volume", "Mean platelet volume (fL)", IcpcUtils.VALIDATOR_NUMBER),
  HEMATOCRIT        (89, "Hematocrit", "Hematocrit (%)", IcpcUtils.VALIDATOR_NUMBER),
  CHOL              (90, "Chol", "Various cholesterol measurement (e.g. total, LDL, HDL, etc.) (Required)", IcpcUtils.VALIDATOR_BINARY),
  LDL               (91, "LDL", "LDL (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  HDL               (92, "HDL", "HDL (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  TOTAL_CHOLESTEROL (93, "Total_Cholesterol", "Total Cholesterol (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  TRIGLYCERIDES     (94, "Triglycerides", "Triglycerides (mg/dL)", IcpcUtils.VALIDATOR_NUMBER),
  PLAT_AGGR_PHENO   (95, "Plat_Aggr_Pheno", "Platelet Aggregation Phenotypes:  (please specify a daily dose)", IcpcUtils.VALIDATOR_NUMBER),
  INSTRUMENT        (96, "Instrument", "Instrument (Manufacturer/Model)", null),
  INTER_ASSAY_VARIATION(97, "Inter_assay_variation", "Inter/intra assay variation (%)", IcpcUtils.VALIDATOR_NUMBER),
  BLOOD_COLLECTION_TYPE(98, "Blood_collection_type", "Blood collection tube (EDTA, Sodium Citrate (mol/L))", null),
  SAMPLE_TYPE       (99, "Sample_type", "Sample Type (PRP or Whole Blood)", null),
  VERIFY_NOW_BASE   (100, "Verify_Now_base", "Verify Now ADP stimulated Aggregation (baseline)", IcpcUtils.VALIDATOR_BINARY_REQ),
  VERIFY_NOW_POST_LOADING(101, "Verify_Now_post_loading", "Verify Now ADP stimulated Aggregation (post-loading)", IcpcUtils.VALIDATOR_BINARY_REQ),
  OPTICAL_PLATELET_AGGREGOMETRY(102, "Optical_Platelet_Aggregometry", "Optical Platelet Aggregometry", IcpcUtils.VALIDATOR_BINARY),
  PRE_CLOPIDOGREL_PLATELET_AGGREGOMETRY_BASE(103, "Pre_clopidogrel_platelet_aggregometry_base", "Pre-clopidogrel platelet aggregometry (baseline)", IcpcUtils.VALIDATOR_BINARY_REQ),
  POST_CLOPIDOGREL_PLATELET_AGGREGOMETRY(104, "Post_clopidogrel_platelet_aggregometry", "Post-clopidogrel platelet aggregometry", IcpcUtils.VALIDATOR_BINARY_REQ),
  AGGREGOMETRY_AGONIST(105, "Aggregometry_agonist", "Aggregometry agonist", null),
  ADP               (106, "ADP", "ADP", null),
  ARACHADONIC_ACID  (107, "Arachadonic_acid", "Arachadonic acid", null),
  COLLAGEN          (108, "Collagen", "Collagen", null),
  PLATELET_AGGREGOMETRY_METHOD(109, "Platelet_aggregometry_method", "Platelet aggregometry method", null),
  CLOPIDOGREL_LOADING_DOSE(110, "Clopidogrel_loading_dose", "Clopidogrel loading dose", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_BASELINE(111, "PFA_mean_EPI_Collagen_closure_Baseline", "PFA mean of EPI/Collagen closure time Baseline", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_BASELINE(112, "PFA_mean_ADP_Collagen_closure_Baseline", "PFA mean of ADP/Collagen closure time Baseline", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_POST(113, "PFA_mean_EPI_Collagen_closure_Post", "PFA mean of EPI/Collagen closure time Post Clopidogrel Loading Dose (…mg)", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_POST(114, "PFA_mean_ADP_Collagen_closure_Post", "PFA mean of ADP/Collagen closure time Post Clopidogrel Loading Dose (…mg)", IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_PFA  (115, "Time_Loading_PFA", "Time interval between loading dose and PFA platelet aggregation measures", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_STANDARD(116, "PFA_mean_EPI_Collagen_closure_Standard", "PFA mean of EPI/Collagen closure time maintenance dose of Clopidogrel", IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_STANDARD(117, "PFA_mean_ADP_Collagen_closure_Standard", "PFA mean of ADP/Collagen closure time maintenance dose of Clopidogrel", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_BASE(118, "Verify_Now_baseline_Base", "Verify Now ADP stimulated Aggregation (baseline) Base", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_PRU(119, "Verify_Now_baseline_PRU", "Verify Now ADP stimulated Aggregation (baseline) PRU", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_PERCENTINHIBITION(120, "Verify_Now_baseline_percentinhibition", "Verify Now ADP stimulated Aggregation (baseline) % Inhibition", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_BASE(121, "Verify_Now_post_Base", "Verify Now ADP stimulated Aggregation (post loading dose) Base", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_PRU(122, "Verify_Now_post_PRU", "Verify Now ADP stimulated Aggregation (post loading dose) PRU", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_PERCENTINHIBITION(123, "Verify_Now_post_percentinhibition", "Verify Now ADP stimulated Aggregation (post loading dose) % Inhibition", IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_VERIFYNOW(124, "Time_loading_VerifyNow", "Time interval between loading dose and Verify Now platelet aggregation measures", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_BASE(125, "Verify_Now_on_clopidogrel_Base", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) Base", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_PRU(126, "Verify_Now_on_clopidogrel_PRU", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_PERCENTINHIBITION(127, "Verify_Now_on_clopidogrel_percentinhibition", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_2(128, "PAP_8_baseline_max_ADP_2", "PAP-8 baseline platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_5(129, "PAP_8_baseline_max_ADP_5", "PAP-8 baseline platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_10(130, "PAP_8_baseline_max_ADP_10", "PAP-8 baseline platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_20(131, "PAP_8_baseline_max_ADP_20", "PAP-8 baseline platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_1(132, "PAP_8_baseline_max_collagen_1", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_2(133, "PAP_8_baseline_max_collagen_2", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_10(134, "PAP_8_baseline_max_collagen_10", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_6(135, "PAP_8_baseline_max_collagen_6", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_EPI(136, "PAP_8_baseline_max_epi", "PAP-8 baseline platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_AA(137, "PAP_8_baseline_max_aa", "PAP-8 baseline platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_1(138, "PAP_8_baseline_lag_collagen_1", "PAP-8 baseline platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_2(139, "PAP_8_baseline_lag_collagen_2", "PAP-8 baseline platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_5(140, "PAP_8_baseline_lag_collagen_5", "PAP-8 baseline platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_10(141, "PAP_8_baseline_lag_collagen_10", "PAP-8 baseline platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_2(142, "PAP_8_post_max_ADP_2", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_5(143, "PAP_8_post_max_ADP_5", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_10(144, "PAP_8_post_max_ADP_10", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_20(145, "PAP_8_post_max_ADP_20", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_1(146, "PAP_8_post_max_collagen_1", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_2(147, "PAP_8_post_max_collagen_2", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_5(148, "PAP_8_post_max_collagen_5", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_10(149, "PAP_8_post_max_collagen_10", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_EPI_PERC(150, "PAP_8_post_max_epi_perc", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_AA_PERC(151, "PAP_8_post_max_aa_perc", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_1(152, "PAP_8_post_lag_collagen_1", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_2(153, "PAP_8_post_lag_collagen_2", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_5(154, "PAP_8_post_lag_collagen_5", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_10(155, "PAP_8_post_lag_collagen_10", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_PAP8 (156, "Time_loading_PAP8", "Time interval between loading dose and PAP-8 platelet aggregation measures", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_2(157, "PAP_8_standard_max_ADP_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_5(158, "PAP_8_standard_max_ADP_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_10(159, "PAP_8_standard_max_ADP_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_20(160, "PAP_8_standard_max_ADP_20", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_1(161, "PAP_8_standard_max_collagen_1", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_2(162, "PAP_8_standard_max_collagen_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_5(163, "PAP_8_standard_max_collagen_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_10(164, "PAP_8_standard_max_collagen_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_EPI_PCT(165, "PAP_8_standard_max_epi_pct", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_AA_PCT(166, "PAP_8_standard_max_aa_pct", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_1(167, "PAP_8_standard_lag_collagen_1", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_2(168, "PAP_8_standard_lag_collagen_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_5(169, "5PAP_8_standard_lag_collagen_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_10(170, "PAP_8_standard_lag_collagen_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_ADP_5(171, "Chronolog_baseline_max_ADP_5", "Chronolog baseline whole blood max aggregation of ADP 5 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_ADP_20(172, "Chronolog_baseline_max_ADP_20", "Chronolog baseline whole blood max aggregation of ADP 20 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_AA(173, "Chronolog_baseline_max_aa", "Chronolog baseline whole blood max aggregation of Arachadonic Acid in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_COLLAGEN1(174, "Chronolog_baseline_max_collagen1", "Chronolog baseline whole blood max aggregation of Collagen 1 µg/mlin ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_ADP_5(175, "Chronolog_baseline_lag_ADP_5", "Chronolog baseline whole blood lag time of ADP 5 µg/mlin seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_ADP_20(176, "Chronolog_baseline_lag_ADP_20", "Chronolog baseline whole blood lag time of ADP 20 µg/mlin seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_AA(177, "Chronolog_baseline_lag_aa", "Chronolog baseline whole blood lag time of Arachadonic Acid in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_COLLAGEN1(178, "Chronolog_baseline_lag_collagen1", "Chronolog baseline whole blood lag time of Collagen 1 µg/mlin seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_ADP_5(179, "Chronolog_loading_max_ADP_5", "Chronolog Plavix post loading dose whole blood max aggregation of ADP 5 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_ADP_20(180, "Chronolog_loading_max_ADP_20", "Chronolog Plavix post loading dose whole blood max aggregation of ADP 20 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_AA(181, "Chronolog_loading_max_aa", "Chronolog Plavix post loading dose whole blood max aggregation of Arachadonic Acid in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_COLLAGEN1(182, "Chronolog_loading_max_collagen1", "Chronolog Plavix post loading dose whole blood max aggregation of Collagen 1 µg/mlin ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_ADP_5(183, "Chronolog_loading_lag_ADP_5", "Chronolog Plavix post loading dose whole blood lag time of ADP 5 µM in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_ADP_20(184, "Chronolog_loading_lag_ADP_20", "Chronolog Plavix post loading dose whole blood lag time of ADP 20 µM in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_AA(185, "Chronolog_loading_lag_aa", "Chronolog Plavix post loading dose whole blood lag time of Arachadonic Acid in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_COLLAGEN1(186, "Chronolog_loading_lag_collagen1", "Chronolog Plavix post loading dose whole blood lag time of Collagen 1 µg/mlin seconds", IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_CHRONOLOG(187, "Time_loading_Chronolog", "Time interval between loading dose and Chronolog platelet aggregation measures", IcpcUtils.VALIDATOR_TIME),
  CHRONOLOG_STANDARD_MAX_ADP_5(188, "Chronolog_standard_max_ADP_5", "Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 5 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_ADP_20(189, "Chronolog_standard_max_ADP_20", "Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 20 µM in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_AA(190, "Chronolog_standard_max_aa", "Chronolog Plavix maintenance dose  whole blood max aggregation of Arachadonic Acid in ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_COLLAGEN1(191, "Chronolog_standard_max_collagen1", "Chronolog Plavix maintenance dose  whole blood max aggregation of Collagen 1 µg/mlin ohms", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_ADP_5(192, "Chronolog_standard_lag_ADP_5", "Chronolog Plavix maintenance dose  whole blood lag time of ADP 5 µM in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_ADP_20(193, "Chronolog_standard_lag_ADP_20", "Chronolog Plavix maintenance dose  whole blood lag time of ADP 20 µM in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_AA(194, "Chronolog_standard_lag_aa", "Chronolog Plavix maintenance dose  whole blood lag time of Arachadonic Acid in seconds", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_COLLAGEN1(195, "Chronolog_standard_lag_collagen1", "Chronolog Plavix maintenance dose  whole blood lag time of Collagen 1 µg/mlin seconds", IcpcUtils.VALIDATOR_NUMBER),
  VASP              (196, "VASP", "VASP phosphorylation assay", IcpcUtils.VALIDATOR_NUMBER),
  VASP_LD           (247, "vasp_ld", "VASP phosphorylation assay after LD", IcpcUtils.VALIDATOR_NUMBER),
  VASP_MD           (248, "vasp_md", "VASP phosphorylation assay after MD", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LTA_MAX(220, "chronolog_postloading_lta_max", "Chronolog post-loading LTA max aggregation of ADP 20 ug/ml in %", IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LTA_FINAL(221, "chronolog_postloading_lta_final", "Chronolog post-loading LTA final (5min) aggregation of ADP 20 ug/ml in %", IcpcUtils.VALIDATOR_NUMBER),
  CONE_ADP_SURFACE_PCT(311,"cone_adp_surface_pct","Cone and Platelet Analyzer (ADP-induced surface coverage%)", IcpcUtils.VALIDATOR_NUMBER),
  CONE_ADP_SURFACE_SIZE(312,"cone_adp_surface_size","Cone and Platelet Analyzer (ADP-induced average size of platelet aggregates µm2)", IcpcUtils.VALIDATOR_NUMBER),
  MULTIPLATE_ADP_TEST(222, "multiplate_adp_test", "Multiplate ADP post loading", IcpcUtils.VALIDATOR_NUMBER),
  MULTIPLATE_AA_TEST(313,"multiplate_aa_test","Multiplate AA test", IcpcUtils.VALIDATOR_NUMBER),
  CARAT_2MICRO_AGGMAX(240, "carat_2micro_aggmax", "CARAT TX4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_POST_MAX(241, "carat_5micro_post_max", "CARAT TX4 post-loading ADP 5uM AGGmax", IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_POST_LATE(242, "carat_5micro_post_late", "CARAT TX4 post-loading ADP 5uM AGGlate", IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_MAIN_MAX(243, "carat_5micro_main_max", "CARAT TX4 maintenance ADP 5uM AGGmax", IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_MAIN_LATE(244, "carat_5micro_main_late", "CARAT TX4 maintenance ADP 5uM AGGlate", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_2M(308,"pap4_base_max_adp_2m","PAP-4 baseline platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_5M(266,"pap4_base_max_adp_5m","PAP-4 baseline platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_10M(267,"pap4_base_max_adp_10m","PAP-4 baseline platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_20M(268,"pap4_base_max_adp_20m","PAP-4 baseline platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_1(269,"pap4_base_max_col_1","PAP-4 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_2(270,"pap4_base_max_col_2","PAP-4 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_5(271,"pap4_base_max_col_5","PAP-4 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_10(272,"pap4_base_max_col_10","PAP-4 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_EPI(273,"pap4_base_max_epi","PAP-4 baseline platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ARACHADONIC(274,"pap4_base_max_arachadonic","PAP-4 baseline platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_1(275,"pap4_base_lag_col_1","PAP-4 baseline platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_2(276,"pap4_base_lag_col_2","PAP-4 baseline platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_5(277,"pap4_base_lag_col_5","PAP-4 baseline platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAB_COL_10(278,"pap4_base_lab_col_10","PAP-4 baseline platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_2M(279,"pap4_plavix_adp_2m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_5M(280,"pap4_plavix_adp_5m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_10M(281,"pap4_plavix_adp_10m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_20M(282,"pap4_plavix_adp_20m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_1(283,"pap4_plavix_max_col_1","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_2(284,"pap4_plavix_max_col_2","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_5(285,"pap4_plavix_max_col_5","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_10(286,"pap4_plavix_max_col_10","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_EPI(287,"pap4_plavix_max_epi","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_ARACHADONIC(288,"pap4_plavix_max_arachadonic","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_1(289,"pap4_plavix_lag_col_1","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_2(290,"pap4_plavix_lag_col_2","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_5(291,"pap4_plavix_lag_col_5","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_10(292,"pap4_plavix_lag_col_10","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_TO_PAP4(293,"time_loading_to_pap4","Time interval between loading dose and PAP-4 platelet aggregation measures", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_2M(294,"pap4_maint_adp_2m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_5M(295,"pap4_maint_adp_5m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_10M(296,"pap4_maint_adp_10m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_20M(297,"pap4_maint_adp_20m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_1(298,"pap4_maint_max_col_1","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_2(299,"pap4_maint_max_col_2","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_5(300,"pap4_maint_max_col_5","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_10(301,"pap4_maint_max_col_10","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_EPI(302,"pap4_maint_max_epi","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_ARACHADONIC(303,"pap4_maint_max_arachadonic","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_1(304,"pap4_maint_lag_col_1","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_2(305,"pap4_maint_lag_col_2","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_5(306,"pap4_maint_lag_col_5","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_10(307,"pap4_maint_lag_col_10","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%", IcpcUtils.VALIDATOR_NUMBER),
  ADDITION_PHENO    (197, "Addition_Pheno", "Insert additional platelet aggregation phenotypes here", IcpcUtils.VALIDATOR_NUMBER),
  RS1045642         (209, "rs1045642", "ABCB1 genotype A>G (rs1045642)", IcpcUtils.VALIDATOR_BASES),
  RS4244285         (198, "rs4244285", "CYP2C19*2 genotype G>A (rs4244285 )", IcpcUtils.VALIDATOR_BASES),
  RS4986893         (199, "rs4986893", "CYP2C19*3 genotype G>A (rs4986893)", IcpcUtils.VALIDATOR_BASES),
  RS28399504        (200, "rs28399504", "CYP2C19*4 genotype A>G (rs28399504)", IcpcUtils.VALIDATOR_BASES),
  RS56337013        (201, "rs56337013", "CYP2C19*5 genotype C>T (rs56337013 )", IcpcUtils.VALIDATOR_BASES),
  RS72552267        (202, "rs72552267", "CYP2C19*6 genotype G>A (rs72552267  )", IcpcUtils.VALIDATOR_BASES),
  RS72558186        (203, "rs72558186", "CYP2C19*7 genotype T>A (rs72558186 )", IcpcUtils.VALIDATOR_BASES),
  RS41291556        (204, "rs41291556", "CYP2C19*8 genotype T>C (rs41291556)", IcpcUtils.VALIDATOR_BASES),
  RS6413438         (205, "rs6413438", "CYP2C19*10 genotype C>T (rs6413438)", IcpcUtils.VALIDATOR_BASES),
  RS12248560        (206, "rs12248560", "CYP2C19*17  genotype C>T (rs12248560)", IcpcUtils.VALIDATOR_BASES),
  RS662             (207, "rs662", "PON1 genotype T>C (rs662)", IcpcUtils.VALIDATOR_BASES),
  RS854560          (208, "rs854560", "PON1 genotype A>T (rs854560)", IcpcUtils.VALIDATOR_BASES),
  RS4803418         (211, "rs4803418", "CYP2B6*1C genotype G>C (rs4803418)", IcpcUtils.VALIDATOR_BASES),
  RS48034189        (212, "rs48034189", "CYP2B6*1C genotype C>T (rs48034189)", IcpcUtils.VALIDATOR_BASES),
  RS8192719         (213, "rs8192719", "CYP2B6*9 genotype T>C (rs8192719)", IcpcUtils.VALIDATOR_BASES),
  RS2279343         (224, "rs2279343", "CYP2B6*4 genotype A>G (rs2279343)", IcpcUtils.VALIDATOR_BASES),
  RS2242480         (229, "rs2242480", "rs2242480", IcpcUtils.VALIDATOR_BASES),
  RS3213619         (230, "rs3213619", "rs3213619", IcpcUtils.VALIDATOR_BASES),
  RS2032582         (231, "rs2032582", "rs2032582", IcpcUtils.VALIDATOR_BASES),
  RS1057910         (232, "rs1057910", "rs1057910", IcpcUtils.VALIDATOR_BASES),
  RS71647871        (233, "rs71647871", "rs71647871", IcpcUtils.VALIDATOR_BASES),
  RS3745274         (214, "CYP2B6_6", "CYP2B6*6 genotype G>T (rs3745274)", IcpcUtils.VALIDATOR_BASES),
  RS3745274_CYP2B6_9(264, "CYP2B6_9", "CYP2B6*9 genotype G>T (rs3745274)", IcpcUtils.VALIDATOR_BASES),
  CYP2B6_GENOTYPES  (226, "cyp2b6_genotypes", "CYP2B6 genotypes", null),
  CYP2C19_GENOTYPES (227, "cyp2c19_genotypes", "Cyp2C19 genotypes", null),
  OTHER_GENOTYPES   (210, "other_genotypes", "Other Genotypes, please specify", null),
  ABS_WHITE_ON_PLAVIX(215, "Abs_white_on_plavix", "Absolute White cell count (cells/µL) on Plavix", IcpcUtils.VALIDATOR_NUMBER),
  RED_ON_PLAVIX     (216, "Red_on_plavix", "Red cell count (cells/µL) on Plavix", IcpcUtils.VALIDATOR_NUMBER),
  PLATELET_ON_PLAVIX(217, "Platelet_on_plavix", "Platelet count (cells/µL) on Plavix", IcpcUtils.VALIDATOR_NUMBER),
  MEANPLATELETVOL_ON_PLAVIX(218, "MeanPlateletVol_on_plavix", "Mean platelet volume (fL) on Plavix", IcpcUtils.VALIDATOR_NUMBER),
  HEMATOCRIT_ON_PLAVIX(219, "Hematocrit_on_plavix", "Hematocrit (%) on Plavix", IcpcUtils.VALIDATOR_NUMBER),
  DNA_CONCENTRATION (223, "dna_concentration", "DNA concentration", IcpcUtils.VALIDATOR_NUMBER),
  BINNEDAGE         (228, "binnedAge", "Binned Age", null),
  ACE_OR_ANG_INH_BLOCKERS(234, "Ace_or_Ang_inh_blockers", "ACE Inhibitors or Angiotensin receptor blockers", IcpcUtils.VALIDATOR_BINARY),
  TTF_ACS           (235, "ttf_acs", "Time to the first ACS", IcpcUtils.VALIDATOR_NUMBER),
  ACS_DURING_FOLLOWUP(236, "acs_during_followup", "ACS during follow up", IcpcUtils.VALIDATOR_BINARY),
  CLINICAL_SETTING  (237, "clinical_setting", "Clinical seting", IcpcUtils.VALIDATOR_BINARY),
  PRIOR_MI          (238, "prior_mi", "Prior MI", IcpcUtils.VALIDATOR_BINARY),
  PRIOR_PCI         (239, "prior_pci", "Prior PCI", IcpcUtils.VALIDATOR_BINARY),
  MI_DURING_FOLLOWUP(245, "mi_during_followup", "MI during follow up", IcpcUtils.VALIDATOR_BINARY),
  TTF_MI            (246, "ttf_mi", "Time to the first MI", IcpcUtils.VALIDATOR_NUMBER),
  DNA_PLATE         (249, "dna_plate", "Plate Number", IcpcUtils.VALIDATOR_NUMBER),
  DNA_LOCATION      (250, "dna_location", "Location", null),
  DNA_UNITS         (251, "dna_units", "Units", null),
  DNA_VOLUME        (252, "dna_volume", "Volume", IcpcUtils.VALIDATOR_NUMBER),
  DNA_ICPC_PLATE    (253, "dna_icpc_plate", "ICPC Plate Number", null),
  DNA_ICPC_TUBE     (254, "dna_icpc_tube", "ICPC Tube ID", null),
  PICOGREEN_CONCENTRATION(255, "picogreen_concentration", "picogreen concentration", IcpcUtils.VALIDATOR_NUMBER),
  INDICATION_CLOPIDOGREL(256, "indication_clopidogrel", "Indication for Clopidogrel", Pattern.compile("[12345;(NA)]*")),
  PCI_INFORMATION   (257, "pci_information", "PCI Information (for patients with CAD)", Pattern.compile("[1234(NA)]")),
  CLOPIDOGREL_BEFORE_TESTING(258, "clopidogrel_before_testing", "Clopidogrel loading dose before testing", IcpcUtils.VALIDATOR_BINARY_REQ),
  CLOPIDOGREL_DURATION_BEFORE_TESTING(259, "clopidogrel_duration_before_testing", "Duration of clopidogrel treatment before testing", IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_WHILE_ON_CLOPIDOGREL(260, "Verify_Now_while_on_clopidogrel", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel)", IcpcUtils.VALIDATOR_BINARY_REQ),
  HEMOGLOBIN        (261, "hemoglobin", "Hemoglogin (g/dL)", IcpcUtils.VALIDATOR_NUMBER),
  PLASMA_UREA       (262, "plasma_urea", "PLASMA UREA (mmol/L)", IcpcUtils.VALIDATOR_NUMBER),
  PHENO_RAW         (309, "raw_ADP_pheno", "raw_ADP_pheno", IcpcUtils.VALIDATOR_NUMBER),
  PHENO_STD         (310, "std_ADP_pheno", "Standardized Top ranking ADP phenotype from each site (does not consider multiple phenotypes from sites)", IcpcUtils.VALIDATOR_NUMBER),
  RIKEN_PLATE_NUM   (314, "riken_plate_num", "ICPC_RIKEN plate#", IcpcUtils.VALIDATOR_NUMBER),
  RIKEN_LOCATION_NUM(315, "riken_location_num", "RIKEN location #", IcpcUtils.VALIDATOR_NUMBER),
  RIKEN_LOCATION    (316, "riken_location", "RIKEN location", null),
  RIKEN_ID          (317, "riken_new_id", "New ID", IcpcUtils.VALIDATOR_NUMBER),
  ATIVE_MALIGNANCY  (319, "ative_malignancy", "active malignancy", IcpcUtils.VALIDATOR_BINARY),
  CARDIO_CHEOCK_PCI (321, "cardio_cheock_pci", "cardiogenic shock at the time of PCI", IcpcUtils.VALIDATOR_BINARY),
  HYPERCHOLESTEROLEMIA        (322, "hypercholesterolemia","Hypercholesterolemia", IcpcUtils.VALIDATOR_BINARY),
  PERI_ART_DISEASE_BASE       (323, "peri_art_disease_base","peripheral arterial disease at baseline", IcpcUtils.VALIDATOR_BINARY),
  TARGET_VESSEL_REVASC        (324, "target_vessel_revasc", "target vessel revascularization", IcpcUtils.VALIDATOR_BINARY),
  TTF_TARGET_VESSEL_REVASC    (325, "ttf_target_vessel_revasc", "time to the first target vessel revascularization", IcpcUtils.VALIDATOR_NUMBER),
  NONTARGET_VESSEL_REVASC     (326, "nontarget_vessel_revasc","Non-target vessel revascularization", IcpcUtils.VALIDATOR_BINARY),
  TTF_NONTARGET_VESSEL_REVASC (327, "ttf_nontarget_vessel_revasc","time to the first non-target vessel revascularization", IcpcUtils.VALIDATOR_NUMBER),
  HEMORRHARGIC_STROKE         (328, "hemorrhargic_stroke","hemorrhargic stroke", IcpcUtils.VALIDATOR_BINARY),
  TTF_HEMORRHARGIC_STROKE     (329, "ttf_hemorrhargic_stroke","Time to the first hemorrhargic stroke", IcpcUtils.VALIDATOR_NUMBER),
  non_hs_crp                  (330, "non_hs_crp", "non-hs-CRP (mg/L)", IcpcUtils.VALIDATOR_NUMBER),
  cyp2c19_star2_count         (331, "cyp2c19_star2_count", "Cyp2C19 genotypes number of *2", IcpcUtils.VALIDATOR_NUMBER),
  cyp2c19_star17_count        (332, "cyp2c19_star17_count", "Cyp2C19 genotypes number of *17", IcpcUtils.VALIDATOR_NUMBER),
  LVEF_CATEGORY               (333, "lvef_category", "LVEF Category", IcpcUtils.VALIDATOR_TERTIARY),
  CASCADED_STDADP_PHENOTYPE   (334, "cascaded_stdADP_Phenotype", "Cascaded Std ADP Phenotype", IcpcUtils.VALIDATOR_NUMBER),
  STDADP_PHENOTYPE_MAX        (335, "stdADP_Phenotype_max", "Std ADP Phenotype Max", IcpcUtils.VALIDATOR_NUMBER),
  RS7254579                   (336, "rs7254579", "CYP2B6 genotype T>C (rs7254579)", IcpcUtils.VALIDATOR_BASES),
  RS2286823                   (337, "rs2286823", "TMEM120A; POR genotype G>A (rs2286823)", IcpcUtils.VALIDATOR_BASES),
  RS1057868  (338, "rs1057868", "TMEM120A; POR genotype C>T (rs1057868)", IcpcUtils.VALIDATOR_BASES),
  RS2046934  (339, "rs2046934", "MED12L;P2RY12 genotype A>G  (rs2046934)", IcpcUtils.VALIDATOR_BASES),
  RS1472122  (340, "rs1472122", "P2RY13;MED12L;GPR87  genotype G>A (rs1472122)", IcpcUtils.VALIDATOR_BASES),
  RS1799853  (341, "rs1799853", "CYP2C9 genotype C>T (rs1799853)", IcpcUtils.VALIDATOR_BASES),
  RS28371685 (342, "rs28371685", "CYP2C9 genotype C>T (rs28371685)", IcpcUtils.VALIDATOR_BASES),
  RS28365085 (343, "rs28365085", "CYP3A5 genotype A>G (rs28365085)", IcpcUtils.VALIDATOR_BASES),
  RS12041331 (344, "rs12041331", "PEAR1 genotype G>A (rs12041331)", IcpcUtils.VALIDATOR_BASES),
  RS1128503  (345, "rs1128503", "ABCB1 genotype G>A (rs1128503)", IcpcUtils.VALIDATOR_BASES),
  RS168753   (346, "rs168753", "F2R genotype A>T (rs168753)", IcpcUtils.VALIDATOR_BASES),
  RS5918     (347, "rs5918", "ITGB3 genotype T>C (rs5918)", IcpcUtils.VALIDATOR_BASES),
  RS762551   (348, "rs762551", "CYP1A2 genotype A>C (rs762551)", IcpcUtils.VALIDATOR_BASES),
  RS2244613  (349, "rs2244613", "CES1 genotype T>G (rs2244613 )", IcpcUtils.VALIDATOR_BASES),
  RS2302429  (350, "rs2302429", "POR genotype G>A (rs2302429)", IcpcUtils.VALIDATOR_BASES),
  MI_PHENO                    (351, "MI_pheno", "MI_pheno", IcpcUtils.VALIDATOR_BINARY),
  MACE_PHENO2                 (352, "MACE_pheno2", "MACE_pheno2", IcpcUtils.VALIDATOR_BINARY),
  MACE_PHENO2_EX_STROKE       (353, "MACE_pheno2_ExclStroke", "MAC_pheno2_ExclStroke", IcpcUtils.VALIDATOR_BINARY),
  CGS                         (354, "cgs_subject", "CGS Subject", IcpcUtils.VALIDATOR_BINARY),
  RS4803419                   (355, "rs4803419", "rs4803419", IcpcUtils.VALIDATOR_BASES),
  MACE_CRITERIA_3             (356, "MACE_Criteria_3", "Includes pts w/MACE_pheno2 who coded \"NA\" but had a CV Death event", IcpcUtils.VALIDATOR_BINARY),
  MACE_CRITERIA_4             (357, "MACE_Criteria_4", "Includes pts w/MACE_pheno2_ExclStroke who coded \"NA\" but had a CV Death event.", IcpcUtils.VALIDATOR_BINARY),
  MACE_CRITERIA_5             (358, "MACE_Criteria_5", "all patients in who (CV Death AND MI AND ST is available) PLUS (all patients in who CV death OR stroke is 'yes')", IcpcUtils.VALIDATOR_BINARY),
  IN_GWAS    (359, "Included_In_GWAS", "Included in GWAS Analysis", IcpcUtils.VALIDATOR_BINARY)
  ;

  private static ExtendedEnumHelper<Property> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;
  private Pattern m_validator;

  Property(int id, String shortName, String displayName, Pattern validator) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    m_validator = validator;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<>();
    }
    s_extendedEnumHelper.add(this, m_id, m_shortName, m_displayName);
  }

  /**
   * Gets the Id of this enum.
   */
  public int getId() {

    return m_id;
  }

  /**
   * Gets the short name of this enum.
   */
  public String getShortName() {

    return m_shortName;
  }

  /**
   * Gets the display name of this enum.  Will return short name if no display name is defined.
   */
  public String getDisplayName() {

    if (m_displayName != null) {
      return m_displayName;
    }
    return m_shortName;
  }

  public Pattern getValidator() {
    return m_validator;
  }

  public boolean validate(String value) {
    return !(m_validator == IcpcUtils.VALIDATOR_BINARY_REQ && IcpcUtils.isBlank(value))
            && value != null && (m_validator == null || IcpcUtils.isBlank(value) || m_validator.matcher(value).matches());

  }

  public String normalize(String value) throws PgkbException {

    if (IcpcUtils.isBlank(value)) {
      return IcpcUtils.NA;
    }

    String stripped = StringUtils.stripToNull(value);

    // for any allele properties
    if (this.getValidator() == IcpcUtils.VALIDATOR_BASES) {
      char[] valueArray = StringUtils.remove(stripped.toUpperCase(), '/').toCharArray();
      Arrays.sort(valueArray);

      StringBuilder sb = new StringBuilder();
      for (char base : valueArray) {
        if (sb.length()!=0) {
          sb.append("/");
        }
        sb.append(base);
      }
      return sb.toString();
    }

    if (this.getValidator() == IcpcUtils.VALIDATOR_NUMBER) {
      Matcher m = IcpcUtils.VALIDATOR_NUMBER.matcher(stripped);
      if (m.find()) {
        String num = m.group(1);
        if (!IcpcUtils.isBlank(num)) {
          if (this == HEIGHT) {
            return normalizeHeight(num);
          }
          return num;
        }
      }
    }

    switch (this) {
      case SUBJECT_ID:
        return StringUtils.strip(stripped, ",");

      case GENOTYPING:
      case PHENOTYPING:
        Value v = Value.lookupByName(stripped.toLowerCase());
        if (v == null) {
          v = Value.lookupById(Integer.valueOf(stripped));
        }
        return v.getDisplayName();

      case RACE_OMB:
        if (stripped.equalsIgnoreCase("caucasian")) {
          return Race.WHITE.getShortName();
        }
        break;

      case RACE_SELF:
        if (stripped.equalsIgnoreCase("white")) {
          return "caucasian";
        }
        break;

      case GENDER:
        Gender gender = Gender.lookupByName(stripped);
        if (gender != null) {
          return gender.getShortName();
        }
        break;

      case SAMPLE_SOURCE:
        List<String> normalizedTokens = Lists.newArrayList();
        for (String token : Splitter.on(";").split(stripped)) {
          SampleSource source = SampleSource.lookupByName(StringUtils.strip(token));
          if (source!=null) {
            normalizedTokens.add(source.getShortName());
          }
        }
        return Joiner.on(";").join(normalizedTokens);
    }

    return stripped;
  }

  /**
   * Normalizes height values. If the height is obviously wrong (in M) adjust it to proper measure (in CM)
   * @param value the height value as a string
   * @return the normalized height as a string or "NA"
   */
  protected static String normalizeHeight(String value) {
    if (IcpcUtils.isBlank(value)) {
      return IcpcUtils.NA;
    }

    try {
      Double height = Double.valueOf(value);
      if (height<10) {
        height = height*100;
      }
      return String.format("%.0f",height);
    }
    catch (Exception ex) {
      return IcpcUtils.NA;
    }
  }


  /**
   * Looks for the enum with the given name.
   */
  public static Property lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }
}
