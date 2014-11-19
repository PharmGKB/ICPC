--
-- Database: icpc
--

-- --------------------------------------------------------

-- Table structures

CREATE TABLE samples (
  Subject_ID varchar(200) NOT NULL,
  Genotyping decimal(10,0) NOT NULL,
  Phenotyping decimal(10,0) NOT NULL,
  Project integer NOT NULL,
  Gender decimal(10,0) DEFAULT NULL,
  Race_self varchar(200) DEFAULT NULL,
  Race_OMB varchar(200) DEFAULT NULL,
  Ethnicity_reported varchar(200) DEFAULT NULL,
  Ethnicity_OMB varchar(200) DEFAULT NULL,
  Country varchar(200) DEFAULT NULL,
  Age decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (Subject_ID)
);

create index samples_prj_idx on samples(project);

create table sampleSources (
  subject_id varchar(200) not null,
  source int not null
);

CREATE TABLE propertyNames (
  name varchar(200) DEFAULT NULL,
  descrip varchar(4000) DEFAULT NULL,
  datatype varchar(100) DEFAULT NULL,
  format varchar(4000) default null,
  idx serial,
  PRIMARY KEY (idx)
);

create index propertyNames_idx on propertyNames(descrip);

CREATE TABLE sampleProperties (
  subjectid varchar(200) DEFAULT NULL,
  datakey varchar(200) DEFAULT NULL,
  datavalue varchar(200) DEFAULT NULL
);

create index sampleProperties_idx on sampleProperties(subjectid);

drop table properties;
create table properties (
  subject_id varchar(200) not null references samples(subject_id),
  property_id integer not null,
  value varchar(200) default null
);

create index properties_sub_idx on properties(subject_id);
create index properties_subprop_idx on properties(subject_id,property_id);

-- -------------------------

-- Properties that are read from excel templates and then stored in the DB

truncate propertyNames;
insert into propertyNames (name, descrip, datatype, format) values ('Sample_Source', 'Tissue Sample Source  Available for Phenotyping and/or QC', 'string', 'Serum=1, Plasma=2, Tissue=3, Others=4, please use ";"  to separate if multiple numbers are entered.');
insert into propertyNames (name, descrip, datatype, format) values ('Gender', 'Gender', 'number', 'Gender; 1=male, 2=female');
insert into propertyNames (name, descrip, datatype, format) values ('Race_OMB', 'Race (OMB)', 'string', 'Racial categories used are as defined by the Office of Management and Budget, which can be found at http://grants2.nih.gov/grants/guide/notice-files/NOT-OD-01-053.html');
insert into propertyNames (name, descrip, datatype, format) values ('Ethnicity_reported', 'Ethnicity (Reported)  (not required for minimal dataset)', 'string', 'Self-reported information');
insert into propertyNames (name, descrip, datatype, format) values ('Ethnicity_OMB', 'Ethnicity (OMB)', 'string', 'OMB ethnicity; ethnicity categories used are as defined by the Office of Management and Budget, which can be found at http://grants2.nih.gov/grants/guide/notice-files/NOT-OD-01-053.html');
insert into propertyNames (name, descrip, datatype, format) values ('Country', 'Country of Origin', 'string', 'Birthplace of Subject');
insert into propertyNames (name, descrip, datatype, format) values ('Race_self', 'Race (self-reported)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Age', 'Age at Time of Consent', 'number', 'age reported in years or unknown = 99 ');
insert into propertyNames (name, descrip, datatype, format) values ('Height', 'Height (cm)', 'number', 'Report in centimeters');
insert into propertyNames (name, descrip, datatype, format) values ('Weight', 'Weight (kg)', 'number', 'Report in kilograms');
insert into propertyNames (name, descrip, datatype, format) values ('BMI', 'BMI (not required for minimal dataset)', 'number', '');
insert into propertyNames (name, descrip, datatype, format) values ('Comorbidities', 'Comorbidities (Disease States)', 'string', 'List of diseases co-occuring in the patient (Free text, each disease separated by a semi-colon)');
insert into propertyNames (name, descrip, datatype, format) values ('Diabetes', 'Diabetes status', 'number', 'Type 1 Diabetes = 1, Type 2 Diabetes = 2, not present = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Ever_Smoked', 'Ever Smoked', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Current_smoker', 'Current smoker', 'number', 'yes = 1, not present = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Blood_Pressure', 'Blood Pressure/Hypertension (required for minimal dataset)', 'number', 'yes = 1, not present = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('BUN', 'BUN (mg/dL)', 'number', 'numerical');
insert into propertyNames (name, descrip, datatype, format) values ('Creatinine', 'Creatinine level (mg/dL)', 'number', 'numerical');
insert into propertyNames (name, descrip, datatype, format) values ('Creatinine_Category', 'Creatinine Category', 'number', '0= creatinine levels<2.5 mg/dl; 1= creatinine levels>=2.5 mg/dl; NA= missing datum');
insert into propertyNames (name, descrip, datatype, format) values ('Alcohol', 'Alcohol intake', 'number', 'none = 0, infrequent =1, moderate=2, frequent=3, or not known = NA');
insert into propertyNames (name, descrip, datatype, format) values ('Diastolic_BP_Max', 'Diastolic BP_Max', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Diastolic_BP_Median', 'Diastolic BP_Median', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Systolic_BP_Max', 'Systolic BP_Max', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Systolic_BP_Median', 'Systolic BP_Median', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('placebo_RCT', 'Availability of platelet aggregation or clinical outcomes from the placebo arm of RCTs for analysis of effect modification (not part of minimal dataset)', 'number', 'yes = 1, not present = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Clopidogrel', 'Clopidogrel therapy?', 'number', 'yes=1, no=0');
insert into propertyNames (name, descrip, datatype, format) values ('Dose_Clopidogrel', 'Maintenance Dose of Clopidogrel (mg/day)', 'number', 'Dose given in mg/day');
insert into propertyNames (name, descrip, datatype, format) values ('Left_Ventricle', 'Left Ventricle Ejection Fraction', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Duration_Clopidogrel', 'Duration of Clopidogrel therapy at followup', 'number', 'days');
insert into propertyNames (name, descrip, datatype, format) values ('Dose_Aspirin', 'Therapeutic Dose of Aspirin (mg/day)', 'number', 'Dose given in mg/day');
insert into propertyNames (name, descrip, datatype, format) values ('Duration_Aspirin', 'Duration of Aspirin therapy at followup', 'number', 'days');
insert into propertyNames (name, descrip, datatype, format) values ('Statins', 'Statins', 'number', 'yes=1, no=0, or not known=99');
insert into propertyNames (name, descrip, datatype, format) values ('PPI', 'PPIs', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('PPI_name', 'Please provide the names of PPIs used', 'number', 'esomeprazole =1, lansoprazole=2, Omeprazole=3, Pantoprazole=4, Rabeprazole=5, Others=6');
insert into propertyNames (name, descrip, datatype, format) values ('Calcium_blockers', 'Calcium Channel Blockers', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Beta_blockers', 'Beta Blockers', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('ACE_Inh', 'ACE Inhibitors', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Ang_inh_blockers', 'Angiotensin receptor blockers', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Ezetemib', 'Ezetemib', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Glycoprotein_IIaIIIb_inhibitor', 'Glycoprotein IIa IIIb inhibitor use (required)', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Active_metabolite', 'Clopidogrel active metabolite levels (optional)', 'number', 'please use value in ng/mL');
insert into propertyNames (name, descrip, datatype, format) values ('Bleeding', 'Bleeding/Other adverse events reporting', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Major_Bleeding', 'Major Bleeding Events', 'number', 'yes = 1, no = 0 or not known = 99                                                                                                          A bleeding event was assessed as serious if it fulfilled the World Health Organization (WHO) criteria for a serious adverse drug reaction, that is, if it was lethal, life-threatening, permanently disabling, or lead to hospital admission or prolongation of hospital stay.');
insert into propertyNames (name, descrip, datatype, format) values ('Minor_Bleeding', 'Minor Bleeding Events', 'number', 'yes = 1, no = 0 or not known = 99;  Any bleed that is not a major bleed');
insert into propertyNames (name, descrip, datatype, format) values ('CV_events', 'Cardiovascular events during followup', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('time_to_mace', 'Time to the first  major adverse cardiac events (MACE) during followup', 'string', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Time_MACE', 'Time to the first major adverse cardiac events (MACE) during followup', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('STEMI', 'STEMI during follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_STEMI', 'Time to the first STEMI', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('NSTEMI', 'NSTEMI during follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Days_MajorBleeding', 'Time to the first major bleeding event during follow up', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Days_MinorBleeding', 'Time to the first minor bleeding event during follow up', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Time_NSTEMI', 'Time to the first NSTEMI', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Time_Angina', 'Time to the first UNSTABLE ANGINA during follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Stroke', 'ischemic CVA (stroke) during follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_stroke', 'Time to the first ischemic CVA during follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Angina', 'UNSTABLE ANGINA during follow up', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('REVASC', 'REVASCULARIZATION (REVASC)  during follow up', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Time_REVASC', 'Time to the first REVASCULARIZATION (REVASC)  during follow up', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('White_cell_count', 'Absolute White cell count (cells/µL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Red_cell_count', 'Red cell count (cells/µL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Platelet_count', 'Platelet count (cells/µL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Mean_platelet_volume', 'Mean platelet volume (fL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Hematocrit', 'Hematocrit (%)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Ejection_fraction', 'Availability of Ejection fraction from the placebo arm of RCTs for analysis of effect modification (optional)', 'number', 'data available =1, not measured = 0');
insert into propertyNames (name, descrip, datatype, format) values ('LDL', 'LDL (mg/dL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('HDL', 'HDL (mg/dL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Total_Cholesterol', 'Total Cholesterol (mg/dL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Triglycerides', 'Triglycerides (mg/dL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Instrument', 'Instrument (Manufacturer/Model)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Inter_assay_variation', 'Inter/intra assay variation (%)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Blood_collection_type', 'Blood collection tube (EDTA, Sodium Citrate (mol/L))', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Sample_type', 'Sample Type (PRP or Whole Blood)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Aggregometry_agonist', 'Aggregometry agonist', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('ADP', 'ADP', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Arachadonic_acid', 'Arachadonic acid', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Collagen', 'Collagen', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Platelet_aggregometry_method', 'Platelet aggregometry method', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Project', 'Project Site', 'string', 'Coded project site where data was collected. ');
insert into propertyNames (name, descrip, datatype, format) values ('rs41291556', 'CYP2C19*8 genotype T>C (rs41291556)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs6413438', 'CYP2C19*10 genotype C>T (rs6413438)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('indication_clopidogrel', 'Indication for Clopidogrel', 'string', 'Coronary artery disease=1; Peripheral arterial disease=2; Ischemic stroke=3; Healthy control=4; Others=5; Not available=NA; please use ";" to separate if multiple numbers are entered');
insert into propertyNames (name, descrip, datatype, format) values ('rs4803418', 'CYP2B6*1C genotype G>C (rs4803418)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs48034189', 'CYP2B6*1C genotype C>T (rs48034189)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs8192719', 'CYP2B6*9 genotype T>C (rs8192719)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs3745274', 'CYP2B6*6 genotype G>T (rs3745274)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Abs_white_on_plavix', 'Absolute White cell count (cells/µL) on Plavix', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Red_on_plavix', 'Red cell count (cells/µL) on Plavix', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Platelet_on_plavix', 'Platelet count (cells/µL) on Plavix', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('MeanPlateletVol_on_plavix', 'Mean platelet volume (fL) on Plavix', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Hematocrit_on_plavix', 'Hematocrit (%) on Plavix', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('chronolog_baseline_lta_max', 'Chronolog post-loading LTA max aggregation of ADP 20 ug/ml in %', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('chronolog_baseline_lta_final', 'Chronolog post-loading LTA final (5min) aggregation of ADP 20 ug/ml in %', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('multiplate_adp_test', 'Multiplate ADP test', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_concentration', 'DNA concentration', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs2279343', 'CYP2B6*4 genotype A>G (rs2279343)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs3745274', 'CYP2B6*9 genotype G>T (rs3745274)', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('cyp2b6_genotypes', 'CYP2B6 genotypes', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('pci_information', 'PCI Information (for patients with CAD)', 'string', 'Stable and no PCI=1; ACS and no PCI=2; Stable and PCI=3; ACS and PCI=4; Not Available =NA; please choose 1 value only, this is only relevant in patients with CAD');
insert into propertyNames (name, descrip, datatype, format) values ('binnedAge', 'Binned Age', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs2242480', 'rs2242480', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs3213619', 'rs3213619', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs2032582', 'rs2032582', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs1057910', 'rs1057910', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('rs71647871', 'rs71647871', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('Ace_or_Ang_inh_blockers', 'ACE Inhibitors or Angiotensin receptor blockers', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('ttf_acs', 'Time to the first ACS', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('acs_during_followup', 'ACS during follow up', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('clinical_setting', 'Stable angina=1, ACS=2', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('prior_mi', 'Prior MI', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('prior_pci', 'Prior PCI', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('carat_2micro_aggmax', 'CARAT TX4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('carat_5micro_post_max', 'CARAT TX4 post-loading ADP 5uM AGGmax', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('carat_5micro_post_late', 'CARAT TX4 post-loading ADP 5uM AGGlate', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('carat_5micro_main_max', 'CARAT TX4 maintenance ADP 5uM AGGmax', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('carat_5micro_main_late', 'CARAT TX4 maintenance ADP 5uM AGGlate', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('mi_during_followup', 'MI during follow up', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('ttf_mi', 'Time to the first MI', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('vasp_ld', 'VASP phosphorylation assay after LD', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('vasp_md', 'VASP phosphorylation assay after MD', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_plate', 'Plate Number', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_location', 'Location', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_units', 'Units', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_volume', 'Volume', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_icpc_plate', 'ICPC Plate Number', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('dna_icpc_tube', 'ICPC Tube ID', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('picogreen_concentration', 'picogreen concentration', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('clopidogrel_before_testing', 'Clopidogrel loading dose before testing', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('clopidogrel_duration_before_testing', 'Duration of clopidogrel treatment before testing', 'string', null);
insert into propertyNames (name, descrip, datatype, format) values ('hemoglobin', 'Hemoglogin (g/dL)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('plasma_urea', 'PLASMA UREA (mmol/L)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('Subject_ID', 'PharmGKB Subject ID', 'string', 'Subject ID numbers in the PharmGKB. Numbers provided by PharmGKB prior to data submission.');
insert into propertyNames (name, descrip, datatype, format) values ('Genotyping', 'Genetic Sample Available for GWAS & Genotyping QC (> 2 µg)', 'number', 'yes = 1, no = 0 ');
insert into propertyNames (name, descrip, datatype, format) values ('Phenotyping', 'Tissue Sample(s) Available for additional Phenotyping or QC', 'number', 'yes = 1, no = 0 ');
insert into propertyNames (name, descrip, datatype, format) values ('CRP', 'hs-CRP (mg/L)', 'number', 'numerical');
insert into propertyNames (name, descrip, datatype, format) values ('Other_ischemic', 'Other ischemic events during follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Congestive_Heart_Failure', 'Congestive Heart Failure and/or Cardiomyopathy duing follow up', 'number', 'yes = 1, not present = 0 or not known = 99 ');
insert into propertyNames (name, descrip, datatype, format) values ('Time_heartFailure', 'Time to the first Congestive Heart Failure and/or Cardiomyopathy duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Mechanical_Valve_Replacement', 'Mechanical Valve Replacement duing follow up', 'number', 'yes = 1, not present = 0, not known = 99 or valve replacement but type not known = 2');
insert into propertyNames (name, descrip, datatype, format) values ('Time_MechValve', 'Time to the first mechanical Valve Replacement duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Tissue_Valve_Replacement', 'Tissue Valve Replacement duing follow up', 'number', 'yes = 1, not present = 0, not known = 99 or valve replacement but type not known = 2');
insert into propertyNames (name, descrip, datatype, format) values ('Time_tissValve', 'Time to the first tissue Valve Replacement duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Stent_thromb', 'Stent Thrombosis duing follow up', 'number', 'left = 1, right =2, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_stent', 'Time to the first Stent Thrombosis duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('All_cause_mortality', 'All cause mortality duing follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_mortality', 'Time to the first All cause mortality duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Cardiovascular_death', 'Cardiovascular death (well adjudicated by committee independent of physician in charge) duing follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_death', 'Time to the first Cardiovascular death duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Atrial_fibrillation', 'Atrial fibrillation duing follow up', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_AF', 'Time to the first Atrial fibrillation duing follow up', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Left_ventricular_hypertrophy', 'Left ventricular hypertrophy duing follow up (optional)', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_venHypertrophy', 'Time to Left ventricular hypertrophy duing follow up (optional)', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Aspirn', 'Aspirin therapy?', 'number', 'yes=1, no=0');
insert into propertyNames (name, descrip, datatype, format) values ('Peripheral_vascular_disease', 'Peripheral vascular disease duing follow up (optional)', 'number', 'yes = 1, no = 0 or not known = 99');
insert into propertyNames (name, descrip, datatype, format) values ('Time_PeriVascular', 'Time to Peripheral vascular disease (optional)', 'number', 'days to the first event since inclusion');
insert into propertyNames (name, descrip, datatype, format) values ('Duration_followup_clinical_outcomes', 'Duration of followup for clinical outcomes', 'number', 'days');
insert into propertyNames (name, descrip, datatype, format) values ('Blood_Cell', 'Blood cell count (white and red) (Optional)', 'number', 'yes=1, no=0, or not known=99');
insert into propertyNames (name, descrip, datatype, format) values ('Chol', 'Various cholesterol measurement (e.g. total, LDL, HDL, etc.) (Required)', 'number', 'yes=1, no=0, or not known=99');
insert into propertyNames (name, descrip, datatype, format) values ('Plat_Aggr_Pheno', 'Platelet Aggregation Phenotypes:  (please specify a daily dose)', 'string', 'Please specify a daily dose wherever the word ''dose'' appears in the table. ');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_base', 'Verify Now ADP stimulated Aggregation (baseline)', 'number', 'yes = 1, no= 0');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_post_loading', 'Verify Now ADP stimulated Aggregation (post-loading)', 'number', 'yes = 1, no= 0');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_while_on_clopidogrel', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel)', 'number', 'yes = 1, no= 0');
insert into propertyNames (name, descrip, datatype, format) values ('Optical_Platelet_Aggregometry', 'Optical Platelet Aggregometry', 'number', '');
insert into propertyNames (name, descrip, datatype, format) values ('Pre_clopidogrel_platelet_aggregometry_base', 'Pre-clopidogrel platelet aggregometry (baseline)', 'number', 'yes = 1, no= 0');
insert into propertyNames (name, descrip, datatype, format) values ('Clopidogrel_loading_dose', 'Clopidogrel loading dose', 'number', 'mg');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_EPI_Collagen_closure_Baseline', 'PFA mean of EPI/Collagen closure time Baseline', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_ADP_Collagen_closure_Baseline', 'PFA mean of ADP/Collagen closure time Baseline', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_EPI_Collagen_closure_Post', 'PFA mean of EPI/Collagen closure time Post Clopidogrel Loading Dose (…mg)', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_ADP_Collagen_closure_Post', 'PFA mean of ADP/Collagen closure time Post Clopidogrel Loading Dose (…mg)', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Time_Loading_PFA', 'Time interval between loading dose and PFA platelet aggregation measures', 'number', 'hours');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_EPI_Collagen_closure_Standard', 'PFA mean of EPI/Collagen closure time maintenance dose of Clopidogrel', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PFA_mean_ADP_Collagen_closure_Standard', 'PFA mean of ADP/Collagen closure time maintenance dose of Clopidogrel', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_baseline_Base', 'Verify Now ADP stimulated Aggregation (baseline) Base', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_baseline_PRU', 'Verify Now ADP stimulated Aggregation (baseline) PRU', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_baseline_percentinhibition', 'Verify Now ADP stimulated Aggregation (baseline) % Inhibition', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_post_Base', 'Verify Now ADP stimulated Aggregation (post loading dose) Base', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_post_PRU', 'Verify Now ADP stimulated Aggregation (post loading dose) PRU', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_post_percentinhibition', 'Verify Now ADP stimulated Aggregation (post loading dose) % Inhibition', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Time_loading_VerifyNow', 'Time interval between loading dose and Verify Now platelet aggregation measures', 'number', 'hours');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_on_clopidogrel_Base', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) Base', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_on_clopidogrel_PRU', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Verify_Now_on_clopidogrel_percentinhibition', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_ADP_2', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_ADP_5', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_ADP_10', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_ADP_20', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_collagen_1', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_collagen_5', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_collagen_10', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_epi_perc', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_aa_perc', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_lag_collagen_1', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_lag_collagen_2', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_lag_collagen_5', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_lag_collagen_10', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Time_loading_PAP8', 'Time interval between loading dose and PAP-8 platelet aggregation measures', 'number', 'hours');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_ADP_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_ADP_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_ADP_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_ADP_20', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_collagen_1', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_collagen_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Post_clopidogrel_platelet_aggregometry', 'Post-clopidogrel platelet aggregometry', 'number', 'yes = 1, no= 0');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_collagen_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_collagen_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_epi_pct', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_max_aa_pct', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_lag_collagen_1', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_lag_collagen_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('5PAP_8_standard_lag_collagen_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_standard_lag_collagen_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_max_ADP_5', 'Chronolog baseline whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_max_ADP_20', 'Chronolog baseline whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_max_aa', 'Chronolog baseline whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_max_collagen1', 'Chronolog Plavix post loading dose whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_lag_ADP_5', 'Chronolog Plavix post loading dose whole blood lag time of ADP 5 µM in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_lag_ADP_20', 'Chronolog Plavix post loading dose whole blood lag time of ADP 20 µM in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_lag_aa', 'Chronolog Plavix post loading dose whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_lag_collagen1', 'Chronolog Plavix post loading dose whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Time_loading_Chronolog', 'Time interval between loading dose and Chronolog platelet aggregation measures', 'number', 'hours');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_max_ADP_5', 'Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_max_ADP_20', 'Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_max_aa', 'Chronolog Plavix maintenance dose  whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_max_collagen1', 'Chronolog Plavix maintenance dose  whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_lag_ADP_5', 'Chronolog Plavix maintenance dose  whole blood lag time of ADP 5 µM in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_lag_ADP_20', 'Chronolog Plavix maintenance dose  whole blood lag time of ADP 20 µM in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_lag_aa', 'Chronolog Plavix maintenance dose  whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_standard_lag_collagen1', 'Chronolog Plavix maintenance dose  whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('VASP', 'VASP phosphorylation assay', 'number', 'Assay result  (numeric), expressed as a Platelet Reactivity Index (PRI) in %');
insert into propertyNames (name, descrip, datatype, format) values ('cyp2c19_genotypes', 'Cyp2C19 genotypes', 'string', 'Please specify for both alleles: *1, *2, *3, *4, *5, *6, *7, *8, *10,  *17');
insert into propertyNames (name, descrip, datatype, format) values ('rs4244285', 'CYP2C19*2 genotype G>A (rs4244285 )', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs4986893', 'CYP2C19*3 genotype G>A (rs4986893)', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs28399504', 'CYP2C19*4 genotype A>G (rs28399504)', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs56337013', 'CYP2C19*5 genotype C>T (rs56337013 )', 'string', 'C/C, C/T, T/T or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs72552267', 'CYP2C19*6 genotype G>A (rs72552267  )', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs72558186', 'CYP2C19*7 genotype T>A (rs72558186 )', 'string', 'T/T, T/A, A/A or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs12248560', 'CYP2C19*17  genotype C>T (rs12248560)', 'string', 'C/C, C/T, T/T or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs662', 'PON1 genotype T>C (rs662)', 'string', 'C/C, C/T, T/T or NA (Note all PON1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs854560', 'PON1 genotype A>T (rs854560)', 'string', 'T/T, T/A, A/A or NA (Note all PON1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('rs1045642', 'ABCB1 genotype A>G (rs1045642)', 'string', 'A/A, A/G, G/G or NA (Note all ABCB1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_collagen_2', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_collagen_10', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_collagen_6', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_epi', 'PAP-8 baseline platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_max_aa', 'PAP-8 baseline platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Addition_Pheno', 'Insert additional platelet aggregation phenotypes here', 'string', '');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_lag_collagen_1', 'PAP-8 baseline platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_lag_collagen_2', 'PAP-8 baseline platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_lag_collagen_5', 'PAP-8 baseline platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_baseline_lag_collagen_10', 'PAP-8 baseline platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_ADP_2', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_ADP_5', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_ADP_10', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_ADP_20', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_collagen_1', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('PAP_8_post_max_collagen_2', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_max_collagen1', 'Chronolog baseline whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_lag_ADP_5', 'Chronolog baseline whole blood lag time of ADP 5 µg/mlin seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_lag_ADP_20', 'Chronolog baseline whole blood lag time of ADP 20 µg/mlin seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_lag_aa', 'Chronolog baseline whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_baseline_lag_collagen1', 'Chronolog baseline whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_max_ADP_5', 'Chronolog Plavix post loading dose whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_max_ADP_20', 'Chronolog Plavix post loading dose whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('Chronolog_loading_max_aa', 'Chronolog Plavix post loading dose whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)');
insert into propertyNames (name, descrip, datatype, format) values ('other_genotypes', 'Other Genotypes, please specify', 'string', 'please use HGNC gene symbols and RsIDs if available');
insert into propertyNames (name, descrip, datatype, format) values ('cone_adp_surface_pct', 'Cone and Platelet Analyzer (ADP-induced surface coverage%)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('cone_adp_surface_size', 'Cone and Platelet Analyzer (ADP-induced average size of platelet aggregates µm2)', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('multiplate_aa_test', 'Multiplate AA test', 'number', null);
insert into propertyNames (name, descrip, datatype, format) values ('riken_plate_num','ICPC_RIKEN plate#','number',null);
insert into propertyNames (name, descrip, datatype, format) values ('riken_location_num','RIKEN location #','number',null);
insert into propertyNames (name, descrip, datatype, format) values ('riken_location', 'RIKEN location','string',null);
insert into propertyNames (name, descrip, datatype, format) values ('riken_new_id', 'New ID','number',null);
commit;
