-- phpMyAdmin SQL Dump
-- version 3.3.9.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 17, 2012 at 11:38 AM
-- Server version: 5.5.9
-- PHP Version: 5.3.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `icpc`
--

-- --------------------------------------------------------

--
-- Table structure for table `samples`
--

CREATE TABLE `samples` (
  `Subject_ID` varchar(200) NOT NULL,
  `Genotyping` decimal(10,0) NOT NULL,
  `Phenotyping` decimal(10,0) NOT NULL,
  `Sample_Source` decimal(10,0) DEFAULT NULL,
  `Project` varchar(200) NOT NULL,
  `Gender` decimal(10,0) DEFAULT NULL,
  `Race_self` varchar(200) DEFAULT NULL,
  `Race_OMB` varchar(200) DEFAULT NULL,
  `Ethnicity_reported` varchar(200) DEFAULT NULL,
  `Ethnicity_OMB` varchar(200) DEFAULT NULL,
  `Country` varchar(200) DEFAULT NULL,
  `Age` decimal(10,0) DEFAULT NULL,
  `Height` decimal(10,0) DEFAULT NULL,
  `Weight` decimal(10,0) DEFAULT NULL,
  `BMI` decimal(10,0) DEFAULT NULL,
  `Comorbidities` varchar(2000) DEFAULT NULL,
  `Diabetes` decimal(10,0) DEFAULT NULL,
  `Ever_Smoked` decimal(10,0) DEFAULT NULL,
  `Current_smoker` decimal(10,0) DEFAULT NULL,
  `Alcohol` decimal(10,0) DEFAULT NULL,
  `Blood_Pressure` decimal(10,0) DEFAULT NULL,
  `Diastolic_BP_Max` decimal(10,0) DEFAULT NULL,
  `Diastolic_BP_Median` decimal(10,0) DEFAULT NULL,
  `Systolic_BP_Max` decimal(10,0) DEFAULT NULL,
  `Systolic_BP_Median` decimal(10,0) DEFAULT NULL,
  `CRP` decimal(10,0) DEFAULT NULL,
  `BUN` decimal(10,0) DEFAULT NULL,
  `Creatinine` decimal(10,0) DEFAULT NULL,
  `Ejection_fraction` decimal(10,0) DEFAULT NULL,
  `Left_Ventricle` decimal(10,0) DEFAULT NULL,
  `placebo_RCT` decimal(10,0) DEFAULT NULL,
  `Clopidogrel` decimal(10,0) NOT NULL,
  `Dose_Clopidogrel` decimal(10,0) DEFAULT NULL,
  `Duration_Clopidogrel` decimal(10,0) DEFAULT NULL,
  `Aspirin` decimal(10,0) NOT NULL,
  `Dose_Aspirin` decimal(10,0) DEFAULT NULL,
  `Duration_Aspirin` decimal(10,0) DEFAULT NULL,
  `Statins` decimal(10,0) DEFAULT NULL,
  `PPI` decimal(10,0) DEFAULT NULL,
  `PPI_name` decimal(10,0) DEFAULT NULL,
  `Calcium_blockers` decimal(10,0) DEFAULT NULL,
  `Beta_blockers` decimal(10,0) DEFAULT NULL,
  `ACE_Inh` decimal(10,0) DEFAULT NULL,
  `Ang_inh_blockers` decimal(10,0) DEFAULT NULL,
  `Ezetemib` decimal(10,0) DEFAULT NULL,
  `Glycoprotein_IIaIIIb_inhibitor` decimal(10,0) DEFAULT NULL,
  `Active_metabolite` decimal(10,0) DEFAULT NULL,
  `CV_events` decimal(10,0) DEFAULT NULL,
  `Time_MACE` decimal(10,0) DEFAULT NULL,
  `Bleeding` decimal(10,0) DEFAULT NULL,
  `Major_Bleeding` decimal(10,0) DEFAULT NULL,
  `Minor_Bleeding` decimal(10,0) DEFAULT NULL,
  `Days_MajorBleeding` decimal(10,0) DEFAULT NULL,
  `Days_MinorBleeding` decimal(10,0) DEFAULT NULL,
  `STEMI` decimal(10,0) DEFAULT NULL,
  `Time_STEMI` decimal(10,0) DEFAULT NULL,
  `NSTEMI` decimal(10,0) DEFAULT NULL,
  `Time_NSTEMI` decimal(10,0) DEFAULT NULL,
  `Angina` decimal(10,0) DEFAULT NULL,
  `Time_Angina` decimal(10,0) DEFAULT NULL,
  `REVASC` decimal(10,0) DEFAULT NULL,
  `Time_REVASC` decimal(10,0) DEFAULT NULL,
  `Stroke` decimal(10,0) DEFAULT NULL,
  `Time_stroke` decimal(10,0) DEFAULT NULL,
  `Other_ischemic` decimal(10,0) DEFAULT NULL,
  `Congestive_Heart_Failure` decimal(10,0) DEFAULT NULL,
  `Time_heartFailure` decimal(10,0) DEFAULT NULL,
  `Mechanical_Valve_Replacement` decimal(10,0) DEFAULT NULL,
  `Time_MechValve` decimal(10,0) DEFAULT NULL,
  `Tissue_Valve_Replacement` decimal(10,0) DEFAULT NULL,
  `Time_tissValve` decimal(10,0) DEFAULT NULL,
  `Stent_thromb` decimal(10,0) DEFAULT NULL,
  `Time_stent` decimal(10,0) DEFAULT NULL,
  `All_cause_mortality` decimal(10,0) DEFAULT NULL,
  `Time_mortality` decimal(10,0) DEFAULT NULL,
  `Cardiovascular_death` decimal(10,0) DEFAULT NULL,
  `Time_death` decimal(10,0) DEFAULT NULL,
  `Left_ventricular_hypertrophy` decimal(10,0) DEFAULT NULL,
  `Time_venHypertrophy` decimal(10,0) DEFAULT NULL,
  `Peripheral_vascular_disease` decimal(10,0) DEFAULT NULL,
  `Time_PeriVascular` decimal(10,0) DEFAULT NULL,
  `Atrial_fibrillation` decimal(10,0) DEFAULT NULL,
  `Time_AF` decimal(10,0) DEFAULT NULL,
  `Duration_followup_clinical_outcomes` decimal(10,0) DEFAULT NULL,
  `Blood_Cell` decimal(10,0) DEFAULT NULL,
  `White_cell_count` decimal(10,0) DEFAULT NULL,
  `Red_cell_count` decimal(10,0) DEFAULT NULL,
  `Platelet_count` decimal(10,0) DEFAULT NULL,
  `Mean_platelet_volume` decimal(10,0) DEFAULT NULL,
  `Hematocrit` decimal(10,0) DEFAULT NULL,
  `Chol` decimal(10,0) DEFAULT NULL,
  `LDL` decimal(10,0) DEFAULT NULL,
  `HDL` decimal(10,0) DEFAULT NULL,
  `Total_Cholesterol` decimal(10,0) DEFAULT NULL,
  `Triglycerides` decimal(10,0) DEFAULT NULL,
  `Plat_Aggr_Pheno` varchar(200) DEFAULT NULL,
  `Instrument` varchar(200) DEFAULT NULL,
  `Inter_assay_variation` decimal(10,0) DEFAULT NULL,
  `Intra_assay_variation` decimal(10,0) DEFAULT NULL,
  `Blood_collection_type` varchar(200) DEFAULT NULL,
  `Sample_type` varchar(200) DEFAULT NULL,
  `Verify_Now_base` decimal(10,0) DEFAULT NULL,
  `Verify_Now_post_loading` decimal(10,0) DEFAULT NULL,
  `Verify_Now_while_on_clopidogrel` decimal(10,0) DEFAULT NULL,
  `Optical_Platelet_Aggregometry` decimal(10,0) DEFAULT NULL,
  `Pre_clopidogrel_platelet_aggregometry_base` decimal(10,0) DEFAULT NULL,
  `Post_clopidogrel_platelet_aggregometry` decimal(10,0) DEFAULT NULL,
  `Aggregometry_agonist` varchar(200) DEFAULT NULL,
  `ADP` varchar(200) DEFAULT NULL,
  `Arachadonic_acid` varchar(200) DEFAULT NULL,
  `Collagen` varchar(200) DEFAULT NULL,
  `Platelet_aggregometry_method` varchar(200) DEFAULT NULL,
  `Clopidogrel_loading_dose` decimal(10,0) DEFAULT NULL,
  `PFA_mean_EPI_Collagen_closure_Baseline` decimal(10,0) DEFAULT NULL,
  `PFA_mean_ADP_Collagen_closure_Baseline` decimal(10,0) DEFAULT NULL,
  `PFA_mean_EPI_Collagen_closure_Post` decimal(10,0) DEFAULT NULL,
  `PFA_mean_ADP_Collagen_closure_Post` decimal(10,0) DEFAULT NULL,
  `Time_Loading_PFA` decimal(10,0) DEFAULT NULL,
  `PFA_mean_EPI_Collagen_closure_Standard` decimal(10,0) DEFAULT NULL,
  `PFA_mean_ADP_Collagen_closure_Standard` decimal(10,0) DEFAULT NULL,
  `Verify_Now_baseline_Base` decimal(10,0) DEFAULT NULL,
  `Verify_Now_baseline_PRU` decimal(10,0) DEFAULT NULL,
  `Verify_Now_baseline_percentinhibition` decimal(10,0) DEFAULT NULL,
  `Verify_Now_post_Base` decimal(10,0) DEFAULT NULL,
  `Verify_Now_post_PRU` decimal(10,0) DEFAULT NULL,
  `Verify_Now_post_percentinhibition` decimal(10,0) DEFAULT NULL,
  `Time_loading_VerifyNow` decimal(10,0) DEFAULT NULL,
  `Verify_Now_on_clopidogrel_Base` decimal(10,0) DEFAULT NULL,
  `Verify_Now_on_clopidogrel_PRU` decimal(10,0) DEFAULT NULL,
  `Verify_Now_on_clopidogrel_percentinhibition` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_ADP_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_ADP_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_collagen_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_collagen_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_collagen_6` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_epi` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_max_aa` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_lag_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_lag_collagen_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_lag_collagen_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_baseline_lag_collagen_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_ADP_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_ADP_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_collagen_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_collagen_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_collagen_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_epi_perc` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_max_aa_perc` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_lag_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_lag_collagen_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_lag_collagen_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_post_lag_collagen_10` decimal(10,0) DEFAULT NULL,
  `Time_loading_PAP8` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_ADP_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_ADP_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_collagen_2` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_collagen_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_collagen_10` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_epi_pct` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_max_aa_pct` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_lag_collagen_1` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_lag_collagen_2` decimal(10,0) DEFAULT NULL,
  `5PAP_8_standard_lag_collagen_5` decimal(10,0) DEFAULT NULL,
  `PAP_8_standard_lag_collagen_10` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_max_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_max_collagen1` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_lag_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_lag_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_lag_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_baseline_lag_collagen1` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_max_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_max_collagen1` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_lag_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_lag_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_lag_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_loading_lag_collagen1` decimal(10,0) DEFAULT NULL,
  `Time_loading_Chronolog` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_max_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_max_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_max_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_max_collagen1` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_lag_ADP_5` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_lag_ADP_20` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_lag_aa` decimal(10,0) DEFAULT NULL,
  `Chronolog_standard_lag_collagen1` decimal(10,0) DEFAULT NULL,
  `VASP` decimal(10,0) DEFAULT NULL,
  `Addition_Pheno` varchar(200) DEFAULT NULL,
  `cyp2c19_genotypes` varchar(200) DEFAULT NULL,
  `rs4244285` varchar(200) DEFAULT NULL,
  `rs4986893` varchar(200) DEFAULT NULL,
  `rs28399504` varchar(200) DEFAULT NULL,
  `rs56337013` varchar(200) DEFAULT NULL,
  `rs72552267` varchar(200) DEFAULT NULL,
  `rs72558186` varchar(200) DEFAULT NULL,
  `rs41291556` varchar(200) DEFAULT NULL,
  `rs6413438` varchar(200) DEFAULT NULL,
  `rs12248560` varchar(200) DEFAULT NULL,
  `rs662` varchar(200) DEFAULT NULL,
  `rs854560` varchar(200) DEFAULT NULL,
  `rs1045642` varchar(200) DEFAULT NULL,
  `other_genotypes` varchar(200) DEFAULT NULL,
  rs4803418 varchar(200) default null,
  rs48034189 varchar(200) default null,
  rs8192719 varchar(200) default null,
  rs3745274 varchar(200) default null,
  Abs_white_on_plavix decimal(10,0) default null,
  Red_on_plavix decimal(10,0) default null,
  Platelet_on_plavix decimal(10,0) default null,
  MeanPlateletVol_on_plavix decimal(10,0) default null,
  Hematocrit_on_plavix decimal(10,0) default null,
  PRIMARY KEY (`Subject_ID`),
  KEY `sample_project_idx` (`Project`)
);

create table sampleGenotypes (
  subject_id varchar(200) not null,
  sortOrder int not null,
  genotype varchar(10) not null
);

create table sampleSources (
subject_id varchar(200) not null,
source int not null
);

create table samplePpiNames (
subject_id varchar(200) not null,
ppiName varchar(200) not null
);

CREATE TABLE propertyNames (
  `name` varchar(200) DEFAULT NULL,
  descrip varchar(4000) DEFAULT NULL,
  datatype varchar(100) DEFAULT NULL,
  idx int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (idx)
);

CREATE TABLE sampleProperties (
  subjectid varchar(200) DEFAULT NULL,
  datakey varchar(200) DEFAULT NULL,
  datavalue varchar(200) DEFAULT NULL
);

---------------------------

-- Properties that are read from excel templates and then stored in the DB

insert into propertyNames(name,descrip,datatype) values ('Subject_ID','PharmGKB Subject ID','string');
insert into propertyNames(name,descrip,datatype) values ('Genotyping','Genetic Sample Available for GWAS & Genotyping QC (> 2 痢)','number');
insert into propertyNames(name,descrip,datatype) values ('Phenotyping','Tissue Sample Source Available for Phenotyping and/or QC','number');
insert into propertyNames(name,descrip,datatype) values ('Sample_Source ','Tissue Sample Source Available for Phenotyping and/or QC','string');
insert into propertyNames(name,descrip,datatype) values ('Project','Project Site','string');
insert into propertyNames(name,descrip,datatype) values ('Gender','Gender','number');
insert into propertyNames(name,descrip,datatype) values ('Race_self','Race (self-reported)','string');
insert into propertyNames(name,descrip,datatype) values ('Race_OMB','Race (OMB)','string');
insert into propertyNames(name,descrip,datatype) values ('Ethnicity_reported','Ethnicity (Reported) (not required for minimal dataset)','string');
insert into propertyNames(name,descrip,datatype) values ('Ethnicity_OMB','Ethnicity (OMB)','string');
insert into propertyNames(name,descrip,datatype) values ('Country','Country of Origin','string');
insert into propertyNames(name,descrip,datatype) values ('Age','Age at Time of Consent','number');
insert into propertyNames(name,descrip,datatype) values ('Height','Height (cm)','number');
insert into propertyNames(name,descrip,datatype) values ('Weight','Weight (kg)','number');
insert into propertyNames(name,descrip,datatype) values ('BMI','BMI (not required for minimal dataset)','number');
insert into propertyNames(name,descrip,datatype) values ('Comorbidities','Comorbidities (Disease States)','string');
insert into propertyNames(name,descrip,datatype) values ('Diabetes','Diabetes status','number');
insert into propertyNames(name,descrip,datatype) values ('Ever_Smoked','Ever Smoked','number');
insert into propertyNames(name,descrip,datatype) values ('Current_smoker','Current smoker','number');
insert into propertyNames(name,descrip,datatype) values ('Alcohol','Alcohol intake','number');
insert into propertyNames(name,descrip,datatype) values ('Blood_Pressure','Blood Pressure/Hypertension (required for minimal dataset)','number');
insert into propertyNames(name,descrip,datatype) values ('Diastolic_BP_Max','Diastolic BP_Max','number');
insert into propertyNames(name,descrip,datatype) values ('Diastolic_BP_Median','Diastolic BP_Median','number');
insert into propertyNames(name,descrip,datatype) values ('Systolic_BP_Max','Systolic BP_Max','number');
insert into propertyNames(name,descrip,datatype) values ('Systolic_BP_Median','Systolic BP_Median','number');
insert into propertyNames(name,descrip,datatype) values ('CRP','hs-CRP (mg/L)','number');
insert into propertyNames(name,descrip,datatype) values ('BUN','BUN (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('Creatinine','Creatinine level (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('Ejection_fraction','Availability of Ejection fraction from the placebo arm of RCTs for analysis of effect modification (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('Left_Ventricle','Left Ventricle Ejection Fraction','number');
insert into propertyNames(name,descrip,datatype) values ('placebo_RCT','Availabililty of platelet aggregation or clinical outcomes from the placebo arm of RCTs for analysis of effect modification (not part of minimal dataset)','number');
insert into propertyNames(name,descrip,datatype) values ('Clopidogrel','Clopidogrel therapy?','number');
insert into propertyNames(name,descrip,datatype) values ('Dose_Clopidogrel','Maintenance Dose of Clopidogrel (mg/day)','number');
insert into propertyNames(name,descrip,datatype) values ('Duration_Clopidogrel','Duration of Clopidogrel therapy at followup','number');
insert into propertyNames(name,descrip,datatype) values ('Aspirn','Aspirin therapy?','number');
insert into propertyNames(name,descrip,datatype) values ('Dose_Aspirin','Therapeutic Dose of Aspirin (mg/day)','number');
insert into propertyNames(name,descrip,datatype) values ('Duration_Aspirin','Duration of Aspirin therapy at followup','number');
insert into propertyNames(name,descrip,datatype) values ('Statins','Statins','number');
insert into propertyNames(name,descrip,datatype) values ('PPI ','PPIs','number');
insert into propertyNames(name,descrip,datatype) values ('PPI_name','Please provide the names of PPIs used','number');
insert into propertyNames(name,descrip,datatype) values ('Calcium_blockers','Calcium Channel Blockers','number');
insert into propertyNames(name,descrip,datatype) values ('Beta_blockers','Beta Blockers','number');
insert into propertyNames(name,descrip,datatype) values ('ACE_Inh','ACE Inhibitors','number');
insert into propertyNames(name,descrip,datatype) values ('Ang_inh_blockers','Angiotensin receptor blockers','number');
insert into propertyNames(name,descrip,datatype) values ('Ezetemib','Ezetemib','number');
insert into propertyNames(name,descrip,datatype) values ('Glycoprotein_IIaIIIb_inhibitor','Glycoprotein IIa IIIb inhibitor use (required)','number');
insert into propertyNames(name,descrip,datatype) values ('Active_metabolite','Clopidogrel active metabolite levels (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('CV_events','Cardiovascular events during followup','number');
insert into propertyNames(name,descrip,datatype) values ('Time_MACE','Time to the first major adverse cardiac events (MACE) during followup','string');
insert into propertyNames(name,descrip,datatype) values ('Bleeding','Bleeding/Other adverse events reporting','number');
insert into propertyNames(name,descrip,datatype) values ('Major_Bleeding','Major Bleeding Events','number');
insert into propertyNames(name,descrip,datatype) values ('Minor_Bleeding','Minor Bleeding Events','number');
insert into propertyNames(name,descrip,datatype) values ('Days_MajorBleeding','Time to the first major bleeding event during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Days_MinorBleeding','Time to the first minor bleeding event during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('STEMI','STEMI during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_STEMI','Time to the first STEMI','number');
insert into propertyNames(name,descrip,datatype) values ('NSTEMI','NSTEMI during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_NSTEMI','Time to the first NSTEMI','number');
insert into propertyNames(name,descrip,datatype) values ('Angina','UNSTABLE ANGINA during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_Angina','Time to the first UNSTABLE ANGINA during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('REVASC','REVASCULARIZATION (REVASC) during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_REVASC','Time to the first REVASCULARIZATION (REVASC) during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Stroke','ischemic CVA (stroke) during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_stroke','Time to the first ischemic CVA during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Other_ischemic','Other ischemic events during follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Congestive_Heart_Failure','Congestive Heart Failure and/or Cardiomyopathy duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_heartFailure','Time to the first Congestive Heart Failure and/or Cardiomyopathy duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Mechanical_Valve_Replacement','Mechanical Valve Replacement duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_MechValve','Time to the first mechanical Valve Replacement duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Tissue_Valve_Replacement','Tissue Valve Replacement duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_tissValve','Time to the first tissue Valve Replacement duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Stent_thromb','Stent Thrombosis duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_stent','Time to the first Stent Thrombosis duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('All_cause_mortality','All cause mortality duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_mortality','Time to the first All cause mortality duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Cardiovascular_death','Cardiovascular death (well adjudicated by committee independent of physician in charge) duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_death','Time to the first Cardiovascular death duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Left_ventricular_hypertrophy','Left ventricular hypertrophy duing follow up (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('Time_venHypertrophy','Time to Left ventricular hypertrophy duing follow up (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('Peripheral_vascular_disease','Peripheral vascular disease duing follow up (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('Time_PeriVascular','Time to Peripheral vascular disease (optional)','number');
insert into propertyNames(name,descrip,datatype) values ('Atrial_fibrillation','Atrial fibrillation duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Time_AF','Time to the first Atrial fibrillation duing follow up','number');
insert into propertyNames(name,descrip,datatype) values ('Duration_followup_clinical_outcomes','Duration of followup for clinical outcomes','number');
insert into propertyNames(name,descrip,datatype) values ('Blood_Cell','Blood cell count (white and red) (Optional)','number');
insert into propertyNames(name,descrip,datatype) values ('White_cell_count','Absolute White cell count (cells/無)','number');
insert into propertyNames(name,descrip,datatype) values ('Red_cell_count','Red cell count (cells/無)','number');
insert into propertyNames(name,descrip,datatype) values ('Platelet_count','Platelet count (cells/無)','number');
insert into propertyNames(name,descrip,datatype) values ('Mean_platelet_volume','Mean platelet volume (fL)','number');
insert into propertyNames(name,descrip,datatype) values ('Hematocrit ','Hematocrit (%)','number');
insert into propertyNames(name,descrip,datatype) values ('Chol','Various cholesterol measurement (e.g. total, LDL, HDL, etc.) (Required)','number');
insert into propertyNames(name,descrip,datatype) values ('LDL','LDL (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('HDL','HDL (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('Total_Cholesterol','Total Cholesterol (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('Triglycerides','Triglycerides (mg/dL)','number');
insert into propertyNames(name,descrip,datatype) values ('Plat_Aggr_Pheno','Platelet Aggregation Phenotypes: (please specify a daily dose)','string');
insert into propertyNames(name,descrip,datatype) values ('Instrument','Instrument (Manufacturer/Model)','string');
insert into propertyNames(name,descrip,datatype) values ('Inter_assay_variation','Inter/intra assay variation (%)','number');
insert into propertyNames(name,descrip,datatype) values ('Blood_collection_type','Blood collection tube (EDTA, Sodium Citrate (mol/L))','string');
insert into propertyNames(name,descrip,datatype) values ('Sample_type','Sample Type (PRP or Whole Blood)','string');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_base','Verify Now ADP stimulated Aggregation (baseline)','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_post_loading','Verify Now ADP stimulated Aggregation (post-loading)','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_while_on_clopidogrel','Verify Now ADP stimulated Aggregation (while on standard dose Clopidogrel therapy)','number');
insert into propertyNames(name,descrip,datatype) values ('Optical_Platelet_Aggregometry','Optical Platelet Aggregometry','number');
insert into propertyNames(name,descrip,datatype) values ('Pre_clopidogrel_platelet_aggregometry_base','Pre-clopidogrel platelet aggregometry (baseline)','number');
insert into propertyNames(name,descrip,datatype) values ('Post_clopidogrel_platelet_aggregometry','Post-clopidogrel platelet aggregometry','number');
insert into propertyNames(name,descrip,datatype) values ('Aggregometry_agonist','Aggregometry agonist','string');
insert into propertyNames(name,descrip,datatype) values ('ADP','ADP','string');
insert into propertyNames(name,descrip,datatype) values ('Arachadonic_acid','Arachadonic acid','string');
insert into propertyNames(name,descrip,datatype) values ('Collagen','Collagen','string');
insert into propertyNames(name,descrip,datatype) values ('Platelet_aggregometry_method','Platelet aggregometry method','string');
insert into propertyNames(name,descrip,datatype) values ('Clopidogrel_loading_dose','Clopidogrel loading dose','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_EPI_Collagen_closure_Baseline','PFA mean of EPI/Collagen closure time Baseline','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_ADP_Collagen_closure_Baseline','PFA mean of ADP/Collagen closure time Baseline','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_EPI_Collagen_closure_Post','PFA mean of EPI/Collagen closure time Post Clopidogrel Loading Dose (卌g)','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_ADP_Collagen_closure_Post','PFA mean of ADP/Collagen closure time Post Clopidogrel Loading Dose (卌g)','number');
insert into propertyNames(name,descrip,datatype) values ('Time_Loading_PFA','Time interval between loading dose and PFA platelet aggregation measures','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_EPI_Collagen_closure_Standard','PFA mean of EPI/Collagen closure time maintenance dose of Clopidogrel','number');
insert into propertyNames(name,descrip,datatype) values ('PFA_mean_ADP_Collagen_closure_Standard','PFA mean of ADP/Collagen closure time maintenance dose of Clopidogrel','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_baseline_Base','Verify Now ADP stimulated Aggregation (baseline) Base','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_baseline_PRU','Verify Now ADP stimulated Aggregation (baseline) PRU','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_baseline_percentinhibition','Verify Now ADP stimulated Aggregation (baseline) % Inhibition','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_post_Base','Verify Now ADP stimulated Aggregation (post loading dose) Base','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_post_PRU','Verify Now ADP stimulated Aggregation (post loading dose) PRU','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_post_percentinhibition','Verify Now ADP stimulated Aggregation (post loading dose) % Inhibition','number');
insert into propertyNames(name,descrip,datatype) values ('Time_loading_VerifyNow','Time interval between loading dose and Verify Now platelet aggregation measures','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_on_clopidogrel_Base','Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) Base','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_on_clopidogrel_PRU','Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU','number');
insert into propertyNames(name,descrip,datatype) values ('Verify_Now_on_clopidogrel_percentinhibition','Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_ADP_2 ','PAP-8 baseline platelet rich plasma max aggregation of ADP 2 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_ADP_5','PAP-8 baseline platelet rich plasma max aggregation of ADP 5 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_ADP_10','PAP-8 baseline platelet rich plasma max aggregation of ADP 10 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_ADP_20','PAP-8 baseline platelet rich plasma max aggregation of ADP 20 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_collagen_1','PAP-8 baseline platelet rich plasma max aggregation of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_collagen_2','PAP-8 baseline platelet rich plasma max aggregation of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_collagen_10','PAP-8 baseline platelet rich plasma max aggregation of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_collagen_6','PAP-8 baseline platelet rich plasma max aggregation of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_epi','PAP-8 baseline platelet rich plasma max aggregation of Epi %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_max_aa','PAP-8 baseline platelet rich plasma max aggregation of Arachadonic Acid %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_lag_collagen_1','PAP-8 baseline platelet rich plasma lag time of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_lag_collagen_2','PAP-8 baseline platelet rich plasma lag time of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_lag_collagen_5','PAP-8 baseline platelet rich plasma lag time of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_baseline_lag_collagen_10','PAP-8 baseline platelet rich plasma lag time of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_ADP_2 ','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_ADP_5','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_ADP_10','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_ADP_20','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_collagen_1','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_collagen_2','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_collagen_5','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_collagen_10','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_epi_perc','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Epi %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_max_aa_perc','PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_lag_collagen_1','PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_lag_collagen_2','PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_lag_collagen_5','PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_post_lag_collagen_10','PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('Time_loading_PAP8','Time interval between loading dose and PAP-8 platelet aggregation measures','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_ADP_2','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_ADP_5','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_ADP_10','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_ADP_20','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 然 %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_collagen_1','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_collagen_2','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_collagen_5','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_collagen_10','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_epi_pct','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_max_aa_pct','PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_lag_collagen_1','PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_lag_collagen_2','PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('5PAP_8_standard_lag_collagen_5','PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('PAP_8_standard_lag_collagen_10','PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 痢/ml%','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_max_ADP_5','Chronolog baseline whole blood max aggregation of ADP 5 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_max_ADP_20','Chronolog baseline whole blood max aggregation of ADP 20 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_max_aa','Chronolog baseline whole blood max aggregation of Arachadonic Acid in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_max_collagen1','Chronolog baseline whole blood max aggregation of Collagen 1 痢/mlin ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_lag_ADP_5','Chronolog baseline whole blood lag time of ADP 5 痢/mlin seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_lag_ADP_20','Chronolog baseline whole blood lag time of ADP 20 痢/mlin seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_lag_aa','Chronolog baseline whole blood lag time of Arachadonic Acid in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_baseline_lag_collagen1','Chronolog baseline whole blood lag time of Collagen 1 痢/mlin seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_max_ADP_5','Chronolog Plavix post loading dose whole blood max aggregation of ADP 5 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_max_ADP_20','Chronolog Plavix post loading dose whole blood max aggregation of ADP 20 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_max_aa','Chronolog Plavix post loading dose whole blood max aggregation of Arachadonic Acid in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_max_collagen1','Chronolog Plavix post loading dose whole blood max aggregation of Collagen 1 痢/mlin ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_lag_ADP_5','Chronolog Plavix post loading dose whole blood lag time of ADP 5 然 in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_lag_ADP_20','Chronolog Plavix post loading dose whole blood lag time of ADP 20 然 in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_lag_aa','Chronolog Plavix post loading dose whole blood lag time of Arachadonic Acid in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_loading_lag_collagen1','Chronolog Plavix post loading dose whole blood lag time of Collagen 1 痢/mlin seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Time_loading_Chronolog','Time interval between loading dose and Chronolog platelet aggregation measures','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_max_ADP_5','Chronolog Plavix maintenance dose whole blood max aggregation of ADP 5 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_max_ADP_20','Chronolog Plavix maintenance dose whole blood max aggregation of ADP 20 然 in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_max_aa','Chronolog Plavix maintenance dose whole blood max aggregation of Arachadonic Acid in ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_max_collagen1','Chronolog Plavix maintenance dose whole blood max aggregation of Collagen 1 痢/mlin ohms','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_lag_ADP_5','Chronolog Plavix maintenance dose whole blood lag time of ADP 5 然 in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_lag_ADP_20','Chronolog Plavix maintenance dose whole blood lag time of ADP 20 然 in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_lag_aa','Chronolog Plavix maintenance dose whole blood lag time of Arachadonic Acid in seconds','number');
insert into propertyNames(name,descrip,datatype) values ('Chronolog_standard_lag_collagen1','Chronolog Plavix maintenance dose whole blood lag time of Collagen 1 痢/mlin seconds','number');
insert into propertyNames(name,descrip,datatype) values ('VASP','VASP phosphorylation assay','number');
insert into propertyNames(name,descrip,datatype) values ('Addition_Pheno','Insert additional platelet aggregation phenotypes here','string');
insert into propertyNames(name,descrip,datatype) values ('rs4244285 ','CYP2C19*2 genotype G>A (rs4244285 )','string');
insert into propertyNames(name,descrip,datatype) values ('rs4986893','CYP2C19*3 genotype G>A (rs4986893)','string');
insert into propertyNames(name,descrip,datatype) values ('rs28399504','CYP2C19*4 genotype A>G (rs28399504)','string');
insert into propertyNames(name,descrip,datatype) values ('rs56337013','CYP2C19*5 genotype C>T (rs56337013 )','string');
insert into propertyNames(name,descrip,datatype) values ('rs72552267','CYP2C19*6 genotype G>A (rs72552267 )','string');
insert into propertyNames(name,descrip,datatype) values ('rs72558186','CYP2C19*7 genotype T>A (rs72558186 )','string');
insert into propertyNames(name,descrip,datatype) values ('rs41291556','CYP2C19*8 genotype T>C (rs41291556)','string');
insert into propertyNames(name,descrip,datatype) values ('rs6413438','CYP2C19*10 genotype C>T (rs6413438)','string');
insert into propertyNames(name,descrip,datatype) values ('rs12248560','CYP2C19*17 genotype C>T (rs12248560)','string');
insert into propertyNames(name,descrip,datatype) values ('rs662','PON1 genotype T>C (rs662)','string');
insert into propertyNames(name,descrip,datatype) values ('rs854560','PON1 genotype A>T (rs854560)','string');
insert into propertyNames(name,descrip,datatype) values ('rs1045642','ABCB1 genotype A>G (rs1045642)','string');
insert into propertyNames(name,descrip,datatype) values ('other_genotypes','Other Genotypes, please specify','string');
insert into propertyNames(name,descrip,datatype) values ('rs4803418','CYP2B6*1C genotype G>C (rs4803418)','string');
insert into propertyNames(name,descrip,datatype) values ('rs48034189','CYP2B6*1C genotype C>T (rs48034189)','string');
insert into propertyNames(name,descrip,datatype) values ('rs8192719','CYP2B6*9 genotype T>C (rs8192719)','string');
insert into propertyNames(name,descrip,datatype) values ('rs3745274','CYP2B6*6 genotype G>T (rs3745274)','string');
insert into propertyNames(name,descrip,datatype) values ('Abs_white_on_plavix','Absolute White cell count (cells/無) on Plavix','number');
insert into propertyNames(name,descrip,datatype) values ('Red_on_plavix','Red cell count (cells/無) on Plavix','number');
insert into propertyNames(name,descrip,datatype) values ('Platelet_on_plavix','Platelet count (cells/無) on Plavix','number');
insert into propertyNames(name,descrip,datatype) values ('MeanPlateletVol_on_plavix','Mean platelet volume (fL) on Plavix','number');
insert into propertyNames(name,descrip,datatype) values ('Hematocrit_on_plavix','Hematocrit (%) on Plavix','number');
insert into propertyNames(name,descrip,datatype) values ('ezetimibe','Ezetimibe','number');
insert into propertyNames(name,descrip,datatype) values ('chronolog_baseline_lta_max','Chronolog baseline LTA max aggregation of ADP 20 ug/ml in %','number');
insert into propertyNames(name,descrip,datatype) values ('chronolog_baseline_lta_final','Chronolog baseline LTA  final (5min) aggregation of ADP 20 ug/ml in %','number');
insert into propertyNames(name,descrip,datatype) values ('multiplate_adp_test','Multiplate ADP test','string');
insert into propertyNames(name,descrip,datatype) values ('dna_concentration','DNA concentration','number');
insert into propertyNames(name,descrip,datatype) values ('rs2279343','CYP2B6*4 genotype A>G (rs2279343)','string');
insert into propertyNames(name,descrip,datatype) values ('rs3745274','CYP2B6*9 genotype G>T (rs3745274 )','string');
insert into propertyNames(name,descrip,datatype) values ('cyp2b6_genotypes','CYP2B6 genotypes','string');
insert into propertyNames(name,descrip,datatype) values ('cyp2c19_genotypes', 'Cyp2C19 genotypes','string');
insert into propertyNames(name,descrip,datatype) values ('rs3745274_cyp2b6_9','CYP2B6*9 genotype G>T (rs3745274)','string');
