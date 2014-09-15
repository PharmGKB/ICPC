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
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ryan Whaley
 */
public enum Property implements ExtendedEnum {
  SUBJECT_ID        (0, "Subject_ID", "PharmGKB Subject ID", false, Pattern.compile("(PA\\d+),?")),
  GENOTYPING        (1, "Genotyping", "Genetic Sample Available for GWAS & Genotyping QC (> 2 µg)", true, IcpcUtils.VALIDATOR_BINARY_REQ),
  PHENOTYPING       (2, "Phenotyping", "Tissue Sample(s) Available for additional Phenotyping or QC", true, IcpcUtils.VALIDATOR_BINARY_REQ),
  SAMPLE_SOURCE     (3, "Sample_Source", "Tissue Sample Source  Available for Phenotyping and/or QC", true, SampleSource.validationPattern()),
  PROJECT           (4, "Project", "Project Site", true, Pattern.compile("\\d+")),
  GENDER            (5, "Gender", "Gender", true, IcpcUtils.makeEnumValidator(Gender.class)),
  RACE_SELF         (6, "Race_self", "Race (self-reported)", false, null),
  RACE_OMB          (7, "Race_OMB", "Race (OMB)", false, Race.validationPattern()),
  ETHNICITY_REPORTED(8, "Ethnicity_reported", "Ethnicity (Reported)  (not required for minimal dataset)", false, null),
  ETHNICITY_OMB     (9, "Ethnicity_OMB", "Ethnicity (OMB)", false, null),
  COUNTRY           (10, "Country", "Country of Origin", true, null),
  AGE               (11, "Age", "Age at Time of Consent", true, IcpcUtils.VALIDATOR_NUMBER),
  HEIGHT            (12, "Height", "Height (cm)", false, IcpcUtils.VALIDATOR_NUMBER),
  WEIGHT            (13, "Weight", "Weight (kg)", false, IcpcUtils.VALIDATOR_NUMBER),
  BMI               (14, "BMI", "BMI (not required for minimal dataset)", false, IcpcUtils.VALIDATOR_NUMBER),
  COMORBIDITIES     (15, "Comorbidities", "Comorbidities (Disease States)", false, null),
  DIABETES          (16, "Diabetes", "Diabetes status", false, IcpcUtils.makeEnumValidator(DiabetesStatus.class)),
  EVER_SMOKED       (17, "Ever_Smoked", "Ever Smoked", false, IcpcUtils.VALIDATOR_BINARY),
  CURRENT_SMOKER    (18, "Current_smoker", "Current smoker", false, IcpcUtils.VALIDATOR_BINARY),
  ALCOHOL           (19, "Alcohol", "Alcohol intake", false, IcpcUtils.makeEnumValidator(AlcoholStatus.class)),
  BLOOD_PRESSURE    (20, "Blood_Pressure", "Blood Pressure/Hypertension (required for minimal dataset)", false, IcpcUtils.VALIDATOR_BINARY),
  DIASTOLIC_BP_MAX  (21, "Diastolic_BP_Max", "Diastolic BP_Max", false, IcpcUtils.VALIDATOR_NUMBER),
  DIASTOLIC_BP_MEDIAN(22, "Diastolic_BP_Median", "Diastolic BP_Median", false, IcpcUtils.VALIDATOR_NUMBER),
  SYSTOLIC_BP_MAX   (23, "Systolic_BP_Max", "Systolic BP_Max", false, IcpcUtils.VALIDATOR_NUMBER),
  SYSTOLIC_BP_MEDIAN(24, "Systolic_BP_Median", "Systolic BP_Median", false, IcpcUtils.VALIDATOR_NUMBER),
  CRP               (25, "CRP", "hs-CRP (mg/L)", false, IcpcUtils.VALIDATOR_NUMBER),
  BUN               (26, "BUN", "BUN (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  CREATININE        (27, "Creatinine", "Creatinine level (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  CREATININE_CAT    (265, "Creatinine_Category", "Creatinine Category", false, IcpcUtils.VALIDATOR_BINARY),
  LVEF_AVAIL        (28, "Ejection_fraction", "Availability of Ejection fraction from the placebo arm of RCTs for analysis of effect modification (optional)", false, IcpcUtils.VALIDATOR_BINARY),
  LVEF              (29, "Left_Ventricle", "Left Ventricle Ejection Fraction", false, IcpcUtils.VALIDATOR_NUMBER),
  PLACEBO_RCT       (30, "placebo_RCT", "Availability of platelet aggregation or clinical outcomes from the placebo arm of RCTs for analysis of effect modification (not part of minimal dataset)", false, IcpcUtils.VALIDATOR_BINARY),
  CLOPIDOGREL       (31, "Clopidogrel", "Clopidogrel therapy?", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  DOSE_CLOPIDOGREL  (32, "Dose_Clopidogrel", "Maintenance Dose of Clopidogrel (mg/day)", false, IcpcUtils.VALIDATOR_NUMBER),
  DURATION_CLOPIDOGREL(33, "Duration_Clopidogrel", "Duration of Clopidogrel therapy at followup", false, IcpcUtils.VALIDATOR_NUMBER),
  ASPIRN            (34, "Aspirn", "Aspirin therapy?", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  DOSE_ASPIRIN      (35, "Dose_Aspirin", "Therapeutic Dose of Aspirin (mg/day)", false, IcpcUtils.VALIDATOR_NUMBER),
  DURATION_ASPIRIN  (36, "Duration_Aspirin", "Duration of Aspirin therapy at followup", false, IcpcUtils.VALIDATOR_NUMBER),
  STATINS           (37, "Statins", "Statins", false, IcpcUtils.VALIDATOR_BINARY),
  PPI               (38, "PPI", "PPIs", false, IcpcUtils.VALIDATOR_BINARY),
  PPI_NAME          (39, "PPI_name", "Please provide the names of PPIs used", false, null),
  CALCIUM_BLOCKERS  (40, "Calcium_blockers", "Calcium Channel Blockers", false, IcpcUtils.VALIDATOR_BINARY),
  BETA_BLOCKERS     (41, "Beta_blockers", "Beta Blockers", false, IcpcUtils.VALIDATOR_BINARY),
  ACE_INH           (42, "ACE_Inh", "ACE Inhibitors", false, IcpcUtils.VALIDATOR_BINARY),
  ANG_INH_BLOCKERS  (43, "Ang_inh_blockers", "Angiotensin receptor blockers", false, IcpcUtils.VALIDATOR_BINARY),
  EZETEMIB          (44, "Ezetemib", "Ezetemib", false, IcpcUtils.VALIDATOR_BINARY),
  GLYCOPROTEIN_IIAIIIB_INHIBITOR(45, "Glycoprotein_IIaIIIb_inhibitor", "Glycoprotein IIa IIIb inhibitor use (required)", false, IcpcUtils.VALIDATOR_BINARY),
  ACTIVE_METABOLITE (46, "Active_metabolite", "Clopidogrel active metabolite levels (optional)", false, IcpcUtils.VALIDATOR_NUMBER),
  CV_EVENTS         (47, "CV_events", "Cardiovascular events during followup", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_MACE         (48, "Time_MACE", "Time to the first major adverse cardiac events (MACE) during followup", false, IcpcUtils.VALIDATOR_NUMBER),
  BLEEDING          (49, "Bleeding", "Bleeding/Other adverse events reporting", false, IcpcUtils.VALIDATOR_BINARY),
  MAJOR_BLEEDING    (50, "Major_Bleeding", "Major Bleeding Events", false, IcpcUtils.VALIDATOR_BINARY),
  DAYS_MAJORBLEEDING(52, "Days_MajorBleeding", "Time to the first major bleeding event during follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  MINOR_BLEEDING    (51, "Minor_Bleeding", "Minor Bleeding Events", false, IcpcUtils.VALIDATOR_BINARY),
  DAYS_MINORBLEEDING(53, "Days_MinorBleeding", "Time to the first minor bleeding event during follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  STEMI             (54, "STEMI", "STEMI during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_STEMI        (55, "Time_STEMI", "Time to the first STEMI", false, IcpcUtils.VALIDATOR_NUMBER),
  NSTEMI            (56, "NSTEMI", "NSTEMI during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_NSTEMI       (57, "Time_NSTEMI", "Time to the first NSTEMI", false, IcpcUtils.VALIDATOR_NUMBER),
  ANGINA            (58, "Angina", "UNSTABLE ANGINA during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_ANGINA       (59, "Time_Angina", "Time to the first UNSTABLE ANGINA during follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  REVASC            (60, "REVASC", "REVASCULARIZATION (REVASC)  during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_REVASC       (61, "Time_REVASC", "Time to the first REVASCULARIZATION (REVASC)  during follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  STROKE            (62, "Stroke", "ischemic CVA (stroke) during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_STROKE       (63, "Time_stroke", "Time to the first ischemic CVA during follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  OTHER_ISCHEMIC    (64, "Other_ischemic", "Other ischemic events during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  CONGESTIVE_HEART_FAILURE(65, "Congestive_Heart_Failure", "Congestive Heart Failure and/or Cardiomyopathy duing follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_HEARTFAILURE (66, "Time_heartFailure", "Time to the first Congestive Heart Failure and/or Cardiomyopathy duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  MECHANICAL_VALVE_REPLACEMENT(67, "Mechanical_Valve_Replacement", "Mechanical Valve Replacement duing follow up", false, IcpcUtils.VALIDATOR_THREE),
  TIME_MECHVALVE    (68, "Time_MechValve", "Time to the first mechanical Valve Replacement duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  TISSUE_VALVE_REPLACEMENT(69, "Tissue_Valve_Replacement", "Tissue Valve Replacement duing follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_TISSVALVE    (70, "Time_tissValve", "Time to the first tissue Valve Replacement duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  STENT_THROMB      (71, "Stent_thromb", "Stent Thrombosis duing follow up", false, IcpcUtils.VALIDATOR_THREE),
  TIME_STENT        (72, "Time_stent", "Time to the first Stent Thrombosis duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  ALL_CAUSE_MORTALITY(73, "All_cause_mortality", "All cause mortality duing follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_MORTALITY    (74, "Time_mortality", "Time to the first All cause mortality duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  CARDIOVASCULAR_DEATH(75, "Cardiovascular_death", "Cardiovascular death (well adjudicated by committee independent of physician in charge) duing follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_DEATH        (76, "Time_death", "Time to the first Cardiovascular death duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  LEFT_VENTRICULAR_HYPERTROPHY(77, "Left_ventricular_hypertrophy", "Left ventricular hypertrophy duing follow up (optional)", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_VENHYPERTROPHY(78, "Time_venHypertrophy", "Time to Left ventricular hypertrophy duing follow up (optional)", false, IcpcUtils.VALIDATOR_NUMBER),
  PERIPHERAL_VASCULAR_DISEASE(79, "Peripheral_vascular_disease", "Peripheral vascular disease duing follow up (optional)", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_PERIVASCULAR (80, "Time_PeriVascular", "Time to Peripheral vascular disease (optional)", false, IcpcUtils.VALIDATOR_NUMBER),
  ATRIAL_FIBRILLATION(81, "Atrial_fibrillation", "Atrial fibrillation duing follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TIME_AF           (82, "Time_AF", "Time to the first Atrial fibrillation duing follow up", false, IcpcUtils.VALIDATOR_NUMBER),
  DURATION_FOLLOWUP_CLINICAL_OUTCOMES(83, "Duration_followup_clinical_outcomes", "Duration of followup for clinical outcomes", false, IcpcUtils.VALIDATOR_NUMBER),
  BLOOD_CELL        (84, "Blood_Cell", "Blood cell count (white and red) (Optional)", false, IcpcUtils.VALIDATOR_BINARY),
  WHITE_CELL_COUNT  (85, "White_cell_count", "Absolute White cell count (cells/µL)", false, IcpcUtils.VALIDATOR_NUMBER),
  RED_CELL_COUNT    (86, "Red_cell_count", "Red cell count (cells/µL)", false, IcpcUtils.VALIDATOR_NUMBER),
  PLATELET_COUNT    (87, "Platelet_count", "Platelet count (cells/µL)", false, IcpcUtils.VALIDATOR_NUMBER),
  MEAN_PLATELET_VOLUME(88, "Mean_platelet_volume", "Mean platelet volume (fL)", false, IcpcUtils.VALIDATOR_NUMBER),
  HEMATOCRIT        (89, "Hematocrit", "Hematocrit (%)", false, IcpcUtils.VALIDATOR_NUMBER),
  CHOL              (90, "Chol", "Various cholesterol measurement (e.g. total, LDL, HDL, etc.) (Required)", false, IcpcUtils.VALIDATOR_BINARY),
  LDL               (91, "LDL", "LDL (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  HDL               (92, "HDL", "HDL (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  TOTAL_CHOLESTEROL (93, "Total_Cholesterol", "Total Cholesterol (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  TRIGLYCERIDES     (94, "Triglycerides", "Triglycerides (mg/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  PLAT_AGGR_PHENO   (95, "Plat_Aggr_Pheno", "Platelet Aggregation Phenotypes:  (please specify a daily dose)", false, IcpcUtils.VALIDATOR_NUMBER),
  INSTRUMENT        (96, "Instrument", "Instrument (Manufacturer/Model)", false, null),
  INTER_ASSAY_VARIATION(97, "Inter_assay_variation", "Inter/intra assay variation (%)", false, IcpcUtils.VALIDATOR_NUMBER),
  BLOOD_COLLECTION_TYPE(98, "Blood_collection_type", "Blood collection tube (EDTA, Sodium Citrate (mol/L))", false, null),
  SAMPLE_TYPE       (99, "Sample_type", "Sample Type (PRP or Whole Blood)", false, null),
  VERIFY_NOW_BASE   (100, "Verify_Now_base", "Verify Now ADP stimulated Aggregation (baseline)", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  VERIFY_NOW_POST_LOADING(101, "Verify_Now_post_loading", "Verify Now ADP stimulated Aggregation (post-loading)", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  OPTICAL_PLATELET_AGGREGOMETRY(102, "Optical_Platelet_Aggregometry", "Optical Platelet Aggregometry", false, IcpcUtils.VALIDATOR_BINARY),
  PRE_CLOPIDOGREL_PLATELET_AGGREGOMETRY_BASE(103, "Pre_clopidogrel_platelet_aggregometry_base", "Pre-clopidogrel platelet aggregometry (baseline)", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  POST_CLOPIDOGREL_PLATELET_AGGREGOMETRY(104, "Post_clopidogrel_platelet_aggregometry", "Post-clopidogrel platelet aggregometry", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  AGGREGOMETRY_AGONIST(105, "Aggregometry_agonist", "Aggregometry agonist", false, null),
  ADP               (106, "ADP", "ADP", false, IcpcUtils.VALIDATOR_BINARY),
  ARACHADONIC_ACID  (107, "Arachadonic_acid", "Arachadonic acid", false, IcpcUtils.VALIDATOR_BINARY),
  COLLAGEN          (108, "Collagen", "Collagen", false, IcpcUtils.VALIDATOR_BINARY),
  PLATELET_AGGREGOMETRY_METHOD(109, "Platelet_aggregometry_method", "Platelet aggregometry method", false, null),
  CLOPIDOGREL_LOADING_DOSE(110, "Clopidogrel_loading_dose", "Clopidogrel loading dose", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_BASELINE(111, "PFA_mean_EPI_Collagen_closure_Baseline", "PFA mean of EPI/Collagen closure time Baseline", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_BASELINE(112, "PFA_mean_ADP_Collagen_closure_Baseline", "PFA mean of ADP/Collagen closure time Baseline", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_POST(113, "PFA_mean_EPI_Collagen_closure_Post", "PFA mean of EPI/Collagen closure time Post Clopidogrel Loading Dose (…mg)", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_POST(114, "PFA_mean_ADP_Collagen_closure_Post", "PFA mean of ADP/Collagen closure time Post Clopidogrel Loading Dose (…mg)", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_PFA  (115, "Time_Loading_PFA", "Time interval between loading dose and PFA platelet aggregation measures", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_EPI_COLLAGEN_CLOSURE_STANDARD(116, "PFA_mean_EPI_Collagen_closure_Standard", "PFA mean of EPI/Collagen closure time maintenance dose of Clopidogrel", false, IcpcUtils.VALIDATOR_NUMBER),
  PFA_MEAN_ADP_COLLAGEN_CLOSURE_STANDARD(117, "PFA_mean_ADP_Collagen_closure_Standard", "PFA mean of ADP/Collagen closure time maintenance dose of Clopidogrel", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_BASE(118, "Verify_Now_baseline_Base", "Verify Now ADP stimulated Aggregation (baseline) Base", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_PRU(119, "Verify_Now_baseline_PRU", "Verify Now ADP stimulated Aggregation (baseline) PRU", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_BASELINE_PERCENTINHIBITION(120, "Verify_Now_baseline_percentinhibition", "Verify Now ADP stimulated Aggregation (baseline) % Inhibition", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_BASE(121, "Verify_Now_post_Base", "Verify Now ADP stimulated Aggregation (post loading dose) Base", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_PRU(122, "Verify_Now_post_PRU", "Verify Now ADP stimulated Aggregation (post loading dose) PRU", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_POST_PERCENTINHIBITION(123, "Verify_Now_post_percentinhibition", "Verify Now ADP stimulated Aggregation (post loading dose) % Inhibition", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_VERIFYNOW(124, "Time_loading_VerifyNow", "Time interval between loading dose and Verify Now platelet aggregation measures", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_BASE(125, "Verify_Now_on_clopidogrel_Base", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) Base", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_PRU(126, "Verify_Now_on_clopidogrel_PRU", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_ON_CLOPIDOGREL_PERCENTINHIBITION(127, "Verify_Now_on_clopidogrel_percentinhibition", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_2(128, "PAP_8_baseline_max_ADP_2", "PAP-8 baseline platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_5(129, "PAP_8_baseline_max_ADP_5", "PAP-8 baseline platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_10(130, "PAP_8_baseline_max_ADP_10", "PAP-8 baseline platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_ADP_20(131, "PAP_8_baseline_max_ADP_20", "PAP-8 baseline platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_1(132, "PAP_8_baseline_max_collagen_1", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_2(133, "PAP_8_baseline_max_collagen_2", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_10(134, "PAP_8_baseline_max_collagen_10", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_COLLAGEN_6(135, "PAP_8_baseline_max_collagen_6", "PAP-8 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_EPI(136, "PAP_8_baseline_max_epi", "PAP-8 baseline platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_MAX_AA(137, "PAP_8_baseline_max_aa", "PAP-8 baseline platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_1(138, "PAP_8_baseline_lag_collagen_1", "PAP-8 baseline platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_2(139, "PAP_8_baseline_lag_collagen_2", "PAP-8 baseline platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_5(140, "PAP_8_baseline_lag_collagen_5", "PAP-8 baseline platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_BASELINE_LAG_COLLAGEN_10(141, "PAP_8_baseline_lag_collagen_10", "PAP-8 baseline platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_2(142, "PAP_8_post_max_ADP_2", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_5(143, "PAP_8_post_max_ADP_5", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_10(144, "PAP_8_post_max_ADP_10", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_ADP_20(145, "PAP_8_post_max_ADP_20", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_1(146, "PAP_8_post_max_collagen_1", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_2(147, "PAP_8_post_max_collagen_2", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_5(148, "PAP_8_post_max_collagen_5", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_COLLAGEN_10(149, "PAP_8_post_max_collagen_10", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_MAX_EPI_PERC(150, "PAP_8_post_max_epi_perc", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  RS1045642         (209, "rs1045642", "ABCB1 genotype A>G (rs1045642)", false, IcpcUtils.VALIDATOR_BASES),
  PAP_8_POST_MAX_AA_PERC(151, "PAP_8_post_max_aa_perc", "PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_1(152, "PAP_8_post_lag_collagen_1", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_2(153, "PAP_8_post_lag_collagen_2", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_5(154, "PAP_8_post_lag_collagen_5", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_POST_LAG_COLLAGEN_10(155, "PAP_8_post_lag_collagen_10", "PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_PAP8 (156, "Time_loading_PAP8", "Time interval between loading dose and PAP-8 platelet aggregation measures", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_2(157, "PAP_8_standard_max_ADP_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_5(158, "PAP_8_standard_max_ADP_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_10(159, "PAP_8_standard_max_ADP_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_ADP_20(160, "PAP_8_standard_max_ADP_20", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_1(161, "PAP_8_standard_max_collagen_1", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_2(162, "PAP_8_standard_max_collagen_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_5(163, "PAP_8_standard_max_collagen_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_COLLAGEN_10(164, "PAP_8_standard_max_collagen_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_EPI_PCT(165, "PAP_8_standard_max_epi_pct", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_MAX_AA_PCT(166, "PAP_8_standard_max_aa_pct", "PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_1(167, "PAP_8_standard_lag_collagen_1", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_2(168, "PAP_8_standard_lag_collagen_2", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_5(169, "5PAP_8_standard_lag_collagen_5", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP_8_STANDARD_LAG_COLLAGEN_10(170, "PAP_8_standard_lag_collagen_10", "PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_ADP_5(171, "Chronolog_baseline_max_ADP_5", "Chronolog baseline whole blood max aggregation of ADP 5 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_ADP_20(172, "Chronolog_baseline_max_ADP_20", "Chronolog baseline whole blood max aggregation of ADP 20 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_AA(173, "Chronolog_baseline_max_aa", "Chronolog baseline whole blood max aggregation of Arachadonic Acid in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_MAX_COLLAGEN1(174, "Chronolog_baseline_max_collagen1", "Chronolog baseline whole blood max aggregation of Collagen 1 µg/mlin ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_ADP_5(175, "Chronolog_baseline_lag_ADP_5", "Chronolog baseline whole blood lag time of ADP 5 µg/mlin seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_ADP_20(176, "Chronolog_baseline_lag_ADP_20", "Chronolog baseline whole blood lag time of ADP 20 µg/mlin seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_AA(177, "Chronolog_baseline_lag_aa", "Chronolog baseline whole blood lag time of Arachadonic Acid in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LAG_COLLAGEN1(178, "Chronolog_baseline_lag_collagen1", "Chronolog baseline whole blood lag time of Collagen 1 µg/mlin seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_ADP_5(179, "Chronolog_loading_max_ADP_5", "Chronolog Plavix post loading dose whole blood max aggregation of ADP 5 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_ADP_20(180, "Chronolog_loading_max_ADP_20", "Chronolog Plavix post loading dose whole blood max aggregation of ADP 20 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_AA(181, "Chronolog_loading_max_aa", "Chronolog Plavix post loading dose whole blood max aggregation of Arachadonic Acid in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_MAX_COLLAGEN1(182, "Chronolog_loading_max_collagen1", "Chronolog Plavix post loading dose whole blood max aggregation of Collagen 1 µg/mlin ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_ADP_5(183, "Chronolog_loading_lag_ADP_5", "Chronolog Plavix post loading dose whole blood lag time of ADP 5 µM in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_ADP_20(184, "Chronolog_loading_lag_ADP_20", "Chronolog Plavix post loading dose whole blood lag time of ADP 20 µM in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_AA(185, "Chronolog_loading_lag_aa", "Chronolog Plavix post loading dose whole blood lag time of Arachadonic Acid in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_LOADING_LAG_COLLAGEN1(186, "Chronolog_loading_lag_collagen1", "Chronolog Plavix post loading dose whole blood lag time of Collagen 1 µg/mlin seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_CHRONOLOG(187, "Time_loading_Chronolog", "Time interval between loading dose and Chronolog platelet aggregation measures", false, IcpcUtils.VALIDATOR_TIME),
  CHRONOLOG_STANDARD_MAX_ADP_5(188, "Chronolog_standard_max_ADP_5", "Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 5 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_ADP_20(189, "Chronolog_standard_max_ADP_20", "Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 20 µM in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_AA(190, "Chronolog_standard_max_aa", "Chronolog Plavix maintenance dose  whole blood max aggregation of Arachadonic Acid in ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_MAX_COLLAGEN1(191, "Chronolog_standard_max_collagen1", "Chronolog Plavix maintenance dose  whole blood max aggregation of Collagen 1 µg/mlin ohms", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_ADP_5(192, "Chronolog_standard_lag_ADP_5", "Chronolog Plavix maintenance dose  whole blood lag time of ADP 5 µM in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_ADP_20(193, "Chronolog_standard_lag_ADP_20", "Chronolog Plavix maintenance dose  whole blood lag time of ADP 20 µM in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_AA(194, "Chronolog_standard_lag_aa", "Chronolog Plavix maintenance dose  whole blood lag time of Arachadonic Acid in seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_STANDARD_LAG_COLLAGEN1(195, "Chronolog_standard_lag_collagen1", "Chronolog Plavix maintenance dose  whole blood lag time of Collagen 1 µg/mlin seconds", false, IcpcUtils.VALIDATOR_NUMBER),
  VASP              (196, "VASP", "VASP phosphorylation assay", false, IcpcUtils.VALIDATOR_NUMBER),
  ADDITION_PHENO    (197, "Addition_Pheno", "Insert additional platelet aggregation phenotypes here", false, IcpcUtils.VALIDATOR_NUMBER),
  RS4244285         (198, "rs4244285", "CYP2C19*2 genotype G>A (rs4244285 )", false, IcpcUtils.VALIDATOR_BASES),
  RS4986893         (199, "rs4986893", "CYP2C19*3 genotype G>A (rs4986893)", false, IcpcUtils.VALIDATOR_BASES),
  RS28399504        (200, "rs28399504", "CYP2C19*4 genotype A>G (rs28399504)", false, IcpcUtils.VALIDATOR_BASES),
  RS56337013        (201, "rs56337013", "CYP2C19*5 genotype C>T (rs56337013 )", false, IcpcUtils.VALIDATOR_BASES),
  RS72552267        (202, "rs72552267", "CYP2C19*6 genotype G>A (rs72552267  )", false, IcpcUtils.VALIDATOR_BASES),
  RS72558186        (203, "rs72558186", "CYP2C19*7 genotype T>A (rs72558186 )", false, IcpcUtils.VALIDATOR_BASES),
  RS41291556        (204, "rs41291556", "CYP2C19*8 genotype T>C (rs41291556)", false, IcpcUtils.VALIDATOR_BASES),
  RS6413438         (205, "rs6413438", "CYP2C19*10 genotype C>T (rs6413438)", false, IcpcUtils.VALIDATOR_BASES),
  RS12248560        (206, "rs12248560", "CYP2C19*17  genotype C>T (rs12248560)", false, IcpcUtils.VALIDATOR_BASES),
  RS662             (207, "rs662", "PON1 genotype T>C (rs662)", false, IcpcUtils.VALIDATOR_BASES),
  RS854560          (208, "rs854560", "PON1 genotype A>T (rs854560)", false, IcpcUtils.VALIDATOR_BASES),
  OTHER_GENOTYPES   (210, "other_genotypes", "Other Genotypes, please specify", false, null),
  RS4803418         (211, "rs4803418", "CYP2B6*1C genotype G>C (rs4803418)", false, IcpcUtils.VALIDATOR_BASES),
  RS48034189        (212, "rs48034189", "CYP2B6*1C genotype C>T (rs48034189)", false, IcpcUtils.VALIDATOR_BASES),
  RS8192719         (213, "rs8192719", "CYP2B6*9 genotype T>C (rs8192719)", false, IcpcUtils.VALIDATOR_BASES),
  RS3745274         (214, "rs3745274", "CYP2B6*6 genotype G>T (rs3745274)", false, IcpcUtils.VALIDATOR_BASES),
  ABS_WHITE_ON_PLAVIX(215, "Abs_white_on_plavix", "Absolute White cell count (cells/µL) on Plavix", false, IcpcUtils.VALIDATOR_NUMBER),
  RED_ON_PLAVIX     (216, "Red_on_plavix", "Red cell count (cells/µL) on Plavix", false, IcpcUtils.VALIDATOR_NUMBER),
  PLATELET_ON_PLAVIX(217, "Platelet_on_plavix", "Platelet count (cells/µL) on Plavix", false, IcpcUtils.VALIDATOR_NUMBER),
  MEANPLATELETVOL_ON_PLAVIX(218, "MeanPlateletVol_on_plavix", "Mean platelet volume (fL) on Plavix", false, IcpcUtils.VALIDATOR_NUMBER),
  HEMATOCRIT_ON_PLAVIX(219, "Hematocrit_on_plavix", "Hematocrit (%) on Plavix", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LTA_MAX(220, "chronolog_baseline_lta_max", "Chronolog baseline LTA max aggregation of ADP 20 ug/ml in %", false, IcpcUtils.VALIDATOR_NUMBER),
  CHRONOLOG_BASELINE_LTA_FINAL(221, "chronolog_baseline_lta_final", "Chronolog baseline LTA final (5min) aggregation of ADP 20 ug/ml in %", false, IcpcUtils.VALIDATOR_NUMBER),
  MULTIPLATE_ADP_TEST(222, "multiplate_adp_test", "Multiplate ADP test", false, IcpcUtils.VALIDATOR_NUMBER),
  DNA_CONCENTRATION (223, "dna_concentration", "DNA concentration", false, IcpcUtils.VALIDATOR_NUMBER),
  RS2279343         (224, "rs2279343", "CYP2B6*4 genotype A>G (rs2279343)", false, IcpcUtils.VALIDATOR_BASES),
  CYP2B6_GENOTYPES  (226, "cyp2b6_genotypes", "CYP2B6 genotypes", false, null),
  CYP2C19_GENOTYPES (227, "cyp2c19_genotypes", "Cyp2C19 genotypes", false, null),
  BINNEDAGE         (228, "binnedAge", "Binned Age", false, null),
  RS2242480         (229, "rs2242480", "rs2242480", false, IcpcUtils.VALIDATOR_BASES),
  RS3213619         (230, "rs3213619", "rs3213619", false, IcpcUtils.VALIDATOR_BASES),
  RS2032582         (231, "rs2032582", "rs2032582", false, IcpcUtils.VALIDATOR_BASES),
  RS1057910         (232, "rs1057910", "rs1057910", false, IcpcUtils.VALIDATOR_BASES),
  RS71647871        (233, "rs71647871", "rs71647871", false, IcpcUtils.VALIDATOR_BASES),
  ACE_OR_ANG_INH_BLOCKERS(234, "Ace_or_Ang_inh_blockers", "ACE Inhibitors or Angiotensin receptor blockers", false, IcpcUtils.VALIDATOR_BINARY),
  TTF_ACS           (235, "ttf_acs", "Time to the first ACS", false, IcpcUtils.VALIDATOR_NUMBER),
  ACS_DURING_FOLLOWUP(236, "acs_during_followup", "ACS during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  CLINICAL_SETTING  (237, "clinical_setting", "Clinical seting", false, IcpcUtils.VALIDATOR_BINARY),
  PRIOR_MI          (238, "prior_mi", "Prior MI", false, IcpcUtils.VALIDATOR_BINARY),
  PRIOR_PCI         (239, "prior_pci", "Prior PCI", false, IcpcUtils.VALIDATOR_BINARY),
  CARAT_2MICRO_AGGMAX(240, "carat_2micro_aggmax", "CARAT TX4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_POST_MAX(241, "carat_5micro_post_max", "CARAT TX4 post-loading ADP 5uM AGGmax", false, IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_POST_LATE(242, "carat_5micro_post_late", "CARAT TX4 post-loading ADP 5uM AGGlate", false, IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_MAIN_MAX(243, "carat_5micro_main_max", "CARAT TX4 maintenance ADP 5uM AGGmax", false, IcpcUtils.VALIDATOR_NUMBER),
  CARAT_5MICRO_MAIN_LATE(244, "carat_5micro_main_late", "CARAT TX4 maintenance ADP 5uM AGGlate", false, IcpcUtils.VALIDATOR_NUMBER),
  MI_DURING_FOLLOWUP(245, "mi_during_followup", "MI during follow up", false, IcpcUtils.VALIDATOR_BINARY),
  TTF_MI            (246, "ttf_mi", "Time to the first MI", false, IcpcUtils.VALIDATOR_NUMBER),
  VASP_LD           (247, "vasp_ld", "VASP phosphorylation assay after LD", false, IcpcUtils.VALIDATOR_NUMBER),
  VASP_MD           (248, "vasp_md", "VASP phosphorylation assay after MD", false, IcpcUtils.VALIDATOR_NUMBER),
  DNA_PLATE         (249, "dna_plate", "Plate Number", false, IcpcUtils.VALIDATOR_NUMBER),
  DNA_LOCATION      (250, "dna_location", "Location", false, null),
  DNA_UNITS         (251, "dna_units", "Units", false, null),
  DNA_VOLUME        (252, "dna_volume", "Volume", false, IcpcUtils.VALIDATOR_NUMBER),
  DNA_ICPC_PLATE    (253, "dna_icpc_plate", "ICPC Plate Number", false, null),
  DNA_ICPC_TUBE     (254, "dna_icpc_tube", "ICPC Tube ID", false, null),
  PICOGREEN_CONCENTRATION(255, "picogreen_concentration", "picogreen concentration", false, IcpcUtils.VALIDATOR_NUMBER),
  INDICATION_CLOPIDOGREL(256, "indication_clopidogrel", "Indication for Clopidogrel", false, Pattern.compile("[12345;(NA)]*")),
  PCI_INFORMATION   (257, "pci_information", "PCI Information (for patients with CAD)", false, Pattern.compile("[1234(NA)]")),
  CLOPIDOGREL_BEFORE_TESTING(258, "clopidogrel_before_testing", "Clopidogrel loading dose before testing", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  CLOPIDOGREL_DURATION_BEFORE_TESTING(259, "clopidogrel_duration_before_testing", "Duration of clopidogrel treatment before testing", false, IcpcUtils.VALIDATOR_NUMBER),
  VERIFY_NOW_WHILE_ON_CLOPIDOGREL(260, "Verify_Now_while_on_clopidogrel", "Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel)", false, IcpcUtils.VALIDATOR_BINARY_REQ),
  HEMOGLOBIN        (261, "hemoglobin", "Hemoglogin (g/dL)", false, IcpcUtils.VALIDATOR_NUMBER),
  PLASMA_UREA       (262, "plasma_urea", "PLASMA UREA (mmol/L)", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_TO_MACE      (263, "time_to_mace", "Time to the first  major adverse cardiac events (MACE) during followup", false, IcpcUtils.VALIDATOR_NUMBER),
  RS3745274_CYP2B6_9(264, "rs3745274_cyp2b6_9","CYP2B6*9 genotype G>T (rs3745274)", false, IcpcUtils.VALIDATOR_BASES),
  PAP4_BASE_MAX_ADP_2M(308,"pap4_base_max_adp_2m","PAP-4 baseline platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_5M(266,"pap4_base_max_adp_5m","PAP-4 baseline platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_10M(267,"pap4_base_max_adp_10m","PAP-4 baseline platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ADP_20M(268,"pap4_base_max_adp_20m","PAP-4 baseline platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_1(269,"pap4_base_max_col_1","PAP-4 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_2(270,"pap4_base_max_col_2","PAP-4 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_5(271,"pap4_base_max_col_5","PAP-4 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_COL_10(272,"pap4_base_max_col_10","PAP-4 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_EPI(273,"pap4_base_max_epi","PAP-4 baseline platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_MAX_ARACHADONIC(274,"pap4_base_max_arachadonic","PAP-4 baseline platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_1(275,"pap4_base_lag_col_1","PAP-4 baseline platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_2(276,"pap4_base_lag_col_2","PAP-4 baseline platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAG_COL_5(277,"pap4_base_lag_col_5","PAP-4 baseline platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_BASE_LAB_COL_10(278,"pap4_base_lab_col_10","PAP-4 baseline platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_2M(279,"pap4_plavix_adp_2m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_5M(280,"pap4_plavix_adp_5m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_10M(281,"pap4_plavix_adp_10m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_ADP_20M(282,"pap4_plavix_adp_20m","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_1(283,"pap4_plavix_max_col_1","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_2(284,"pap4_plavix_max_col_2","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_5(285,"pap4_plavix_max_col_5","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_COL_10(286,"pap4_plavix_max_col_10","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_EPI(287,"pap4_plavix_max_epi","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_MAX_ARACHADONIC(288,"pap4_plavix_max_arachadonic","PAP-4 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_1(289,"pap4_plavix_lag_col_1","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_2(290,"pap4_plavix_lag_col_2","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_5(291,"pap4_plavix_lag_col_5","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_PLAVIX_LAG_COL_10(292,"pap4_plavix_lag_col_10","PAP-4 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  TIME_LOADING_TO_PAP4(293,"time_loading_to_pap4","Time interval between loading dose and PAP-4 platelet aggregation measures", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_2M(294,"pap4_maint_adp_2m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_5M(295,"pap4_maint_adp_5m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_10M(296,"pap4_maint_adp_10m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_ADP_20M(297,"pap4_maint_adp_20m","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_1(298,"pap4_maint_max_col_1","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_2(299,"pap4_maint_max_col_2","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_5(300,"pap4_maint_max_col_5","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_COL_10(301,"pap4_maint_max_col_10","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_EPI(302,"pap4_maint_max_epi","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_MAX_ARACHADONIC(303,"pap4_maint_max_arachadonic","PAP-4 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_1(304,"pap4_maint_lag_col_1","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_2(305,"pap4_maint_lag_col_2","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_5(306,"pap4_maint_lag_col_5","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PAP4_MAINT_LAG_COL_10(307,"pap4_maint_lag_col_10","PAP-4 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%", false, IcpcUtils.VALIDATOR_NUMBER),
  PHENO_RAW(309, "raw_pheno", "raw_pheno", false, IcpcUtils.VALIDATOR_NUMBER),
  PHENO_STD(310, "std_pheno", "std_pheno", false, IcpcUtils.VALIDATOR_NUMBER)
  ;

  private static ExtendedEnumHelper<Property> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;
  private Boolean m_shownInReport;
  private Pattern m_validator;

  Property(int id, String shortName, String displayName, boolean shownInReport, Pattern validator) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    m_shownInReport = shownInReport;
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
   * Looks for the enum with the given Id.
   */
  public static Property lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static Property lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<Property> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<Property> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }

  public Boolean isShownInReport() {
    return m_shownInReport;
  }
}
