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
create index samples_idx2 on samples(subject_id);
create index samples_idx3 on samples(project,subject_id);


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
create index propertyNames_idx2 on propertyNames(idx);


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
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Sample_Source', 'Tissue Sample Source  Available for Phenotyping and/or QC', 'string', 'Serum=1, Plasma=2, Tissue=3, Others=4, please use ";"  to separate if multiple numbers are entered.', 1);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Gender', 'Gender', 'number', 'Gender; 1=male, 2=female', 2);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Race_OMB', 'Race (OMB)', 'string', 'Racial categories used are as defined by the Office of Management and Budget, which can be found at http://grants2.nih.gov/grants/guide/notice-files/NOT-OD-01-053.html', 3);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ethnicity_reported', 'Ethnicity (Reported)  (not required for minimal dataset)', 'string', 'Self-reported information', 4);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ethnicity_OMB', 'Ethnicity (OMB)', 'string', 'OMB ethnicity; ethnicity categories used are as defined by the Office of Management and Budget, which can be found at http://grants2.nih.gov/grants/guide/notice-files/NOT-OD-01-053.html', 5);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Country', 'Country of Origin', 'string', 'Birthplace of Subject', 6);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Race_self', 'Race (self-reported)', 'string', null, 7);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Age', 'Age at Time of Consent', 'number', 'age reported in years or unknown = NA ', 8);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Height', 'Height (cm)', 'number', 'Report in centimeters', 9);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Weight', 'Weight (kg)', 'number', 'Report in kilograms', 10);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('BMI', 'BMI (not required for minimal dataset)', 'number', null, 11);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Comorbidities', 'Comorbidities (Disease States)', 'string', 'List of diseases co-occuring in the patient (Free text, each disease separated by a semi-colon)', 12);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Diabetes', 'Diabetes status', 'number', 'Type 1 Diabetes = 1, Type 2 Diabetes = 2, not present = 0 or not known = NA', 13);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ever_Smoked', 'Ever Smoked', 'number', 'yes = 1, no = 0 or not known = NA', 14);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Current_smoker', 'Current smoker', 'number', 'yes = 1, not present = 0 or not known = NA', 15);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Blood_Pressure', 'Blood Pressure/Hypertension (required for minimal dataset)', 'number', 'yes = 1, not present = 0 or not known = NA', 16);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('BUN', 'BUN (mg/dL)', 'number', 'numerical', 17);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Creatinine', 'Creatinine level (mg/dL)', 'number', 'numerical', 18);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Creatinine_Category', 'Creatinine Category', 'number', '0= creatinine levels<2.5 mg/dl; 1= creatinine levels>=2.5 mg/dl; NA= missing datum', 19);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Alcohol', 'Alcohol intake', 'number', 'none = 0, infrequent =1, moderate=2, frequent=3, or not known = NA', 20);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Diastolic_BP_Max', 'Diastolic BP_Max', 'number', null, 21);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Diastolic_BP_Median', 'Diastolic BP_Median', 'number', null, 22);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Systolic_BP_Max', 'Systolic BP_Max', 'number', null, 23);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Systolic_BP_Median', 'Systolic BP_Median', 'number', null, 24);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('placebo_RCT', 'Availability of platelet aggregation or clinical outcomes from the placebo arm of RCTs for analysis of effect modification (not part of minimal dataset)', 'number', 'yes = 1, not present = 0 or not known = NA', 25);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Clopidogrel', 'Clopidogrel therapy?', 'number', 'yes=1, no=0', 26);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Dose_Clopidogrel', 'Maintenance Dose of Clopidogrel (mg/day)', 'number', 'Dose given in mg/day', 27);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Left_Ventricle', 'Left Ventricle Ejection Fraction', 'number', null, 28);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Duration_Clopidogrel', 'Duration of Clopidogrel therapy at followup', 'number', 'days', 29);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Dose_Aspirin', 'Therapeutic Dose of Aspirin (mg/day)', 'number', 'Dose given in mg/day', 30);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Duration_Aspirin', 'Duration of Aspirin therapy at followup', 'number', 'days', 31);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Statins', 'Statins', 'number', 'yes=1, no=0, or not known=NA', 32);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PPI', 'PPIs', 'number', 'yes = 1, no = 0 or not known = NA', 33);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PPI_name', 'Please provide the names of PPIs used', 'number', 'esomeprazole =1, lansoprazole=2, Omeprazole=3, Pantoprazole=4, Rabeprazole=5, Others=6', 34);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Calcium_blockers', 'Calcium Channel Blockers', 'number', 'yes = 1, no = 0 or not known = NA', 35);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Beta_blockers', 'Beta Blockers', 'number', 'yes = 1, no = 0 or not known = NA', 36);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ACE_Inh', 'ACE Inhibitors', 'number', 'yes = 1, no = 0 or not known = NA', 37);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ang_inh_blockers', 'Angiotensin receptor blockers', 'number', 'yes = 1, no = 0 or not known = NA', 38);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ezetemib', 'Ezetemib', 'number', 'yes = 1, no = 0 or not known = NA', 39);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Glycoprotein_IIaIIIb_inhibitor', 'Glycoprotein IIa IIIb inhibitor use (required)', 'number', 'yes = 1, no = 0 or not known = NA', 40);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Active_metabolite', 'Clopidogrel active metabolite levels (optional)', 'number', 'please use value in ng/mL', 41);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Bleeding', 'Bleeding/Other adverse events reporting', 'number', 'yes = 1, no = 0 or not known = NA', 42);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Major_Bleeding', 'Major Bleeding Events', 'number', 'yes = 1, no = 0 or not known = NA                                                                                                          A bleeding event was assessed as serious if it fulfilled the World Health Organization (WHO) criteria for a serious adverse drug reaction, that is, if it was lethal, life-threatening, permanently disabling, or lead to hospital admission or prolongation of hospital stay.', 43);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Minor_Bleeding', 'Minor Bleeding Events', 'number', 'yes = 1, no = 0 or not known = NA;  Any bleed that is not a major bleed', 44);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('CV_events', 'Cardiovascular events during followup', 'number', 'yes = 1, no = 0 or not known = NA', 45);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('time_to_mace', 'Time to the first  major adverse cardiac events (MACE) during followup', 'string', 'days to the first event since inclusion', 46);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_MACE', 'Time to the first major adverse cardiac events (MACE) during followup', 'string', null, 47);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('STEMI', 'STEMI during follow up', 'number', 'yes = 1, no = 0 or not known = NA', 48);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_STEMI', 'Time to the first STEMI', 'number', 'days to the first event since inclusion', 49);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('NSTEMI', 'NSTEMI during follow up', 'number', 'yes = 1, no = 0 or not known = NA', 50);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Days_MajorBleeding', 'Time to the first major bleeding event during follow up', 'number', null, 51);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Days_MinorBleeding', 'Time to the first minor bleeding event during follow up', 'number', null, 52);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_NSTEMI', 'Time to the first NSTEMI', 'number', 'days to the first event since inclusion', 53);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_Angina', 'Time to the first UNSTABLE ANGINA during follow up', 'number', 'days to the first event since inclusion', 54);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Stroke', 'ischemic CVA (stroke) during follow up', 'number', 'yes = 1, no = 0 or not known = NA', 55);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_stroke', 'Time to the first ischemic CVA during follow up', 'number', 'days to the first event since inclusion', 56);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Angina', 'UNSTABLE ANGINA during follow up', 'number', null, 57);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('REVASC', 'REVASCULARIZATION (REVASC)  during follow up', 'number', null, 58);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_REVASC', 'Time to the first REVASCULARIZATION (REVASC)  during follow up', 'number', null, 59);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('White_cell_count', 'Absolute White cell count (cells/µL)', 'number', null, 60);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Red_cell_count', 'Red cell count (cells/µL)', 'number', null, 61);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Platelet_count', 'Platelet count (cells/µL)', 'number', null, 62);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Mean_platelet_volume', 'Mean platelet volume (fL)', 'number', null, 63);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Hematocrit', 'Hematocrit (%)', 'number', null, 64);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ejection_fraction', 'Availability of Ejection fraction from the placebo arm of RCTs for analysis of effect modification (optional)', 'number', 'data available =1, not  meatured = 0', 65);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('LDL', 'LDL (mg/dL)', 'number', null, 66);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('HDL', 'HDL (mg/dL)', 'number', null, 67);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Total_Cholesterol', 'Total Cholesterol (mg/dL)', 'number', null, 68);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Triglycerides', 'Triglycerides (mg/dL)', 'number', null, 69);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Instrument', 'Instrument (Manufacturer/Model)', 'string', null, 70);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Inter_assay_variation', 'Inter/intra assay variation (%)', 'number', null, 71);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Blood_collection_type', 'Blood collection tube (EDTA, Sodium Citrate (mol/L))', 'string', null, 72);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Sample_type', 'Sample Type (PRP or Whole Blood)', 'string', null, 73);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Aggregometry_agonist', 'Aggregometry agonist', 'string', null, 74);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ADP', 'ADP', 'string', 'yes = 1 (please also specify concentration), no= 0', 75);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Arachadonic_acid', 'Arachadonic acid', 'string', 'yes = 1 (please also specify concentration), no= 0', 76);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Collagen', 'Collagen', 'string', 'yes = 1 (please also specify concentration), no= 0', 77);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Platelet_aggregometry_method', 'Platelet aggregometry method', 'string', null, 78);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Project', 'Project Site', 'string', 'Coded project site where data was collected. This will be filled by PharmGKB', 79);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs41291556', 'CYP2C19*8 genotype T>C (rs41291556)', 'string', null, 80);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs6413438', 'CYP2C19*10 genotype C>T (rs6413438)', 'string', null, 81);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('indication_clopidogrel', 'Indication for Clopidogrel', 'string', 'Coronary artery disease=1; Peripheral arterial disease=2; Ischemic stroke=3; Healthy control=4; Others=5; Not available=NA; please use ";"  to separate if multiple numbers are entered.', 82);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs4803418', 'CYP2B6*1C genotype G>C (rs4803418)', 'string', null, 83);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs48034189', 'CYP2B6*1C genotype C>T (rs48034189)', 'string', null, 84);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs8192719', 'CYP2B6*9 genotype T>C (rs8192719)', 'string', null, 85);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('CYP2B6_6', 'CYP2B6*6 genotype G>T (rs3745274)', 'string', null, 86);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Abs_white_on_plavix', 'Absolute White cell count (cells/µL) on Plavix', 'number', null, 87);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Red_on_plavix', 'Red cell count (cells/µL) on Plavix', 'number', null, 88);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Platelet_on_plavix', 'Platelet count (cells/µL) on Plavix', 'number', null, 89);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('MeanPlateletVol_on_plavix', 'Mean platelet volume (fL) on Plavix', 'number', null, 90);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Hematocrit_on_plavix', 'Hematocrit (%) on Plavix', 'number', null, 91);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('chronolog_postloading_lta_max', 'Chronolog post-loading LTA max aggregation of ADP 20 ug/ml in %', 'number', null, 92);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('chronolog_postloading_lta_final', 'Chronolog post-loading LTA final (5min) aggregation of ADP 20 ug/ml in %', 'number', null, 93);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('multiplate_adp_test', 'Multiplate ADP post loading', 'string', null, 94);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_concentration', 'DNA concentration', 'number', null, 95);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs2279343', 'CYP2B6*4 genotype A>G (rs2279343)', 'string', null, 96);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('CYP2B6_9', 'CYP2B6*9 genotype G>T (rs3745274)', 'string', 'rs3745274_cyp2b6_9', 97);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cyp2b6_genotypes', 'CYP2B6 genotypes', 'string', null, 98);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('pci_information', 'PCI Information (for patients with CAD)', 'string', 'Stable and no PCI=1; ACS and no PCI=2; Stable and PCI=3; ACS and PCI=4;  Not Available =NA; please choose 1 value only, this is only relevant in patients with CAD', 99);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('binnedAge', 'Binned Age', 'string', null, 100);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs2242480', 'rs2242480', 'string', null, 101);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs3213619', 'rs3213619', 'string', null, 102);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs2032582', 'rs2032582', 'string', null, 103);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs1057910', 'rs1057910', 'string', null, 104);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs71647871', 'rs71647871', 'string', null, 105);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Ace_or_Ang_inh_blockers', 'ACE Inhibitors or Angiotensin receptor blockers', 'string', null, 106);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ttf_acs', 'Time to the first ACS', 'string', 'days to the first event since inclusion', 107);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('acs_during_followup', 'ACS during follow up', 'string', 'yes=1, no=0, not known=NA', 108);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('clinical_setting', 'Clinical Setting', 'string', 'Stable angina=1, ACS=2', 109);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('prior_mi', 'Prior MI', 'string', 'yes=1, no=0, not known=NA', 110);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('prior_pci', 'Prior PCI', 'string', 'yes=1, no=0, not known=NA', 111);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('carat_2micro_aggmax', 'CARAT TX4 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %', 'string', null, 112);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('carat_5micro_post_max', 'CARAT TX4 post-loading ADP 5uM AGGmax', 'string', null, 113);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('carat_5micro_post_late', 'CARAT TX4 post-loading ADP 5uM AGGlate', 'string', null, 114);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('carat_5micro_main_max', 'CARAT TX4 maintenance ADP 5uM AGGmax', 'string', null, 115);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('carat_5micro_main_late', 'CARAT TX4 maintenance ADP 5uM AGGlate', 'string', null, 116);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('mi_during_followup', 'MI during follow up', 'string', 'yes=1, no=0, not known=NA', 117);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ttf_mi', 'Time to the first MI', 'string', 'days to the first event since inclusion', 118);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('vasp_ld', 'VASP phosphorylation assay after LD', 'string', 'VASP assay after loading dose (LD), Assay result  (numeric), expressed as a Platelet Reactivity Index (PRI) in %', 119);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('vasp_md', 'VASP phosphorylation assay after MD', 'string', 'VASP assay after maintenance dose (MD), Assay result  (numeric), expressed as a Platelet Reactivity Index (PRI) in %', 120);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_plate', 'Plate Number', 'string', null, 121);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_location', 'Location', 'string', null, 122);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_units', 'Units', 'string', null, 123);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_volume', 'Volume', 'string', null, 124);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_icpc_plate', 'ICPC Plate Number', 'string', null, 125);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('dna_icpc_tube', 'ICPC Tube ID', 'string', null, 126);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('picogreen_concentration', 'picogreen concentration', 'string', null, 127);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('clopidogrel_before_testing', 'Clopidogrel loading dose before testing', 'string', null, 128);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('clopidogrel_duration_before_testing', 'Duration of clopidogrel treatment before testing', 'string', null, 129);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('hemoglobin', 'Hemoglogin (g/dL)', 'number', null, 130);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('plasma_urea', 'PLASMA UREA (mmol/L)', 'number', null, 131);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Subject_ID', 'PharmGKB Subject ID', 'string', 'Subject ID numbers in the PharmGKB. Numbers provided by PharmGKB prior to data submission.', 132);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Genotyping', 'Genetic Sample Available for GWAS & Genotyping QC (> 2 µg)', 'number', 'yes = 1, no = 0 ', 133);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Phenotyping', 'Tissue Sample(s) Available for additional Phenotyping or QC', 'number', 'yes = 1, no = 0 ', 134);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('CRP', 'hs-CRP (mg/L)', 'number', 'numerical', 135);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Other_ischemic', 'Other ischemic events during follow up', 'number', 'yes = 1, no = 0 or not known = NA', 136);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Congestive_Heart_Failure', 'Congestive Heart Failure and/or Cardiomyopathy duing follow up', 'number', 'yes = 1, not present = 0 or not known = NA ', 137);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_heartFailure', 'Time to the first Congestive Heart Failure and/or Cardiomyopathy duing follow up', 'number', 'days to the first event since inclusion', 138);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Mechanical_Valve_Replacement', 'Mechanical Valve Replacement duing follow up', 'number', 'yes = 1, not present = 0, not known = NA or valve replacement but type not known = 2', 139);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_MechValve', 'Time to the first mechanical Valve Replacement duing follow up', 'number', 'days to the first event since inclusion', 140);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Tissue_Valve_Replacement', 'Tissue Valve Replacement duing follow up', 'number', 'yes = 1, not present = 0, not known = NA or valve replacement but type not known = 2', 141);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_tissValve', 'Time to the first tissue Valve Replacement duing follow up', 'number', 'days to the first event since inclusion', 142);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Stent_thromb', 'Stent Thrombosis duing follow up', 'number', 'yes = 1, not present = 0, not known = NA', 143);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_stent', 'Time to the first Stent Thrombosis duing follow up', 'number', 'days to the first event since inclusion', 144);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('All_cause_mortality', 'All cause mortality duing follow up', 'number', 'yes = 1, no = 0 or not known = NA', 145);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_mortality', 'Time to the first All cause mortality duing follow up', 'number', 'days to the first event since inclusion', 146);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Cardiovascular_death', 'Cardiovascular death (well adjudicated by committee independent of physician in charge) duing follow up', 'number', 'yes = 1, no = 0 or not known = NA', 147);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_death', 'Time to the first Cardiovascular death duing follow up', 'number', 'days to the first event since inclusion', 148);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Atrial_fibrillation', 'Atrial fibrillation duing follow up', 'number', 'yes = 1, no = 0 or not known = NA', 149);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_AF', 'Time to the first Atrial fibrillation duing follow up', 'number', 'days to the first event since inclusion', 150);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Left_ventricular_hypertrophy', 'Left ventricular hypertrophy duing follow up (optional)', 'number', 'yes = 1, no = 0 or not known = NA', 151);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_venHypertrophy', 'Time to Left ventricular hypertrophy duing follow up (optional)', 'number', 'days to the first event since inclusion', 152);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Aspirn', 'Aspirin therapy?', 'number', 'yes=1, no=0', 153);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Peripheral_vascular_disease', 'Peripheral vascular disease duing follow up (optional)', 'number', 'yes = 1, no = 0 or not known = NA', 154);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_PeriVascular', 'Time to Peripheral vascular disease (optional)', 'number', 'days to the first event since inclusion', 155);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Duration_followup_clinical_outcomes', 'Duration of followup for clinical outcomes', 'number', 'days', 156);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Blood_Cell', 'Blood cell count (white and red) (Optional)', 'number', 'yes=1, no=0, or not known=NA', 157);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chol', 'Various cholesterol measurement (e.g. total, LDL, HDL, etc.) (Required)', 'number', 'yes=1, no=0, or not known=NA', 158);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Plat_Aggr_Pheno', 'Platelet Aggregation Phenotypes:  (please specify a daily dose)', 'string', 'Please specify a daily dose wherever the word ''dose'' appears in the table. ', 159);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_base', 'Verify Now ADP stimulated Aggregation (baseline)', 'number', 'yes = 1, no= 0', 160);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_post_loading', 'Verify Now ADP stimulated Aggregation (post-loading)', 'number', 'yes = 1, no= 0', 161);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_while_on_clopidogrel', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel)', 'number', 'yes = 1, no= 0', 162);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Optical_Platelet_Aggregometry', 'Optical Platelet Aggregometry', 'number', null, 163);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Pre_clopidogrel_platelet_aggregometry_base', 'Pre-clopidogrel platelet aggregometry (baseline)', 'number', 'yes = 1, no= 0', 164);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Clopidogrel_loading_dose', 'Clopidogrel loading dose', 'number', 'mg', 165);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_EPI_Collagen_closure_Baseline', 'PFA mean of EPI/Collagen closure time Baseline', 'number', 'Assay result  (numeric)', 166);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_ADP_Collagen_closure_Baseline', 'PFA mean of ADP/Collagen closure time Baseline', 'number', 'Assay result  (numeric)', 167);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_EPI_Collagen_closure_Post', 'PFA mean of EPI/Collagen closure time Post Clopidogrel Loading Dose (…mg)', 'number', 'Assay result  (numeric)', 168);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_ADP_Collagen_closure_Post', 'PFA mean of ADP/Collagen closure time Post Clopidogrel Loading Dose (…mg)', 'number', 'Assay result  (numeric)', 169);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_Loading_PFA', 'Time interval between loading dose and PFA platelet aggregation measures', 'number', 'hours', 170);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_EPI_Collagen_closure_Standard', 'PFA mean of EPI/Collagen closure time maintenance dose of Clopidogrel', 'number', 'Assay result  (numeric)', 171);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PFA_mean_ADP_Collagen_closure_Standard', 'PFA mean of ADP/Collagen closure time maintenance dose of Clopidogrel', 'number', 'Assay result  (numeric)', 172);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_baseline_Base', 'Verify Now ADP stimulated Aggregation (baseline) Base', 'number', 'Assay result  (numeric)', 173);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_baseline_PRU', 'Verify Now ADP stimulated Aggregation (baseline) PRU', 'number', 'Assay result  (numeric)', 174);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_baseline_percentinhibition', 'Verify Now ADP stimulated Aggregation (baseline) % Inhibition', 'number', 'Assay result  (numeric)', 175);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_post_Base', 'Verify Now ADP stimulated Aggregation (post loading dose) Base', 'number', 'Assay result  (numeric)', 176);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_post_PRU', 'Verify Now ADP stimulated Aggregation (post loading dose) PRU', 'number', 'Assay result  (numeric)', 177);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_post_percentinhibition', 'Verify Now ADP stimulated Aggregation (post loading dose) % Inhibition', 'number', 'Assay result  (numeric)', 178);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_loading_VerifyNow', 'Time interval between loading dose and Verify Now platelet aggregation measures', 'number', 'hours', 179);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_on_clopidogrel_Base', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) Base', 'number', 'Assay result  (numeric)', 180);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_on_clopidogrel_PRU', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) PRU', 'number', 'Assay result  (numeric)', 181);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Verify_Now_on_clopidogrel_percentinhibition', 'Verify Now ADP stimulated Aggregation (while on maintenance dose of Clopidogrel) % Inhibition', 'number', 'Assay result  (numeric)', 182);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_ADP_2', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)', 183);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_ADP_5', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)', 184);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_ADP_10', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)', 185);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_ADP_20', 'PAP-8 baseline platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)', 186);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_collagen_1', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 187);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_collagen_5', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 188);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_collagen_10', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 189);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_epi_perc', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)', 190);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_aa_perc', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)', 191);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_lag_collagen_1', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 192);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_lag_collagen_2', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 193);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_lag_collagen_5', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 194);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_lag_collagen_10', 'PAP-8 post Plavix loading dose platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 195);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_loading_PAP8', 'Time interval between loading dose and PAP-8 platelet aggregation measures', 'number', 'hours', 196);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_ADP_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)', 197);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_ADP_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)', 198);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_ADP_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)', 199);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_ADP_20', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)', 200);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_collagen_1', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 201);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_collagen_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 202);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Post_clopidogrel_platelet_aggregometry', 'Post-clopidogrel platelet aggregometry', 'number', 'yes = 1, no= 0', 203);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_collagen_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 204);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_collagen_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 205);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_epi_pct', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)', 206);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_max_aa_pct', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)', 207);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_lag_collagen_1', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 208);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_lag_collagen_2', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 209);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('5PAP_8_standard_lag_collagen_5', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 210);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_standard_lag_collagen_10', 'PAP-8 maintenance dose of clopidogrel platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 211);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_max_ADP_5', 'Chronolog baseline whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)', 212);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_max_ADP_20', 'Chronolog baseline whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)', 213);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_max_aa', 'Chronolog baseline whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)', 214);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_max_collagen1', 'Chronolog Plavix post loading dose whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)', 215);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_lag_ADP_5', 'Chronolog Plavix post loading dose whole blood lag time of ADP 5 µM in seconds', 'number', 'Assay result  (numeric)', 216);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_lag_ADP_20', 'Chronolog Plavix post loading dose whole blood lag time of ADP 20 µM in seconds', 'number', 'Assay result  (numeric)', 217);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_lag_aa', 'Chronolog Plavix post loading dose whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)', 218);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_lag_collagen1', 'Chronolog Plavix post loading dose whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)', 219);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Time_loading_Chronolog', 'Time interval between loading dose and Chronolog platelet aggregation measures', 'number', 'hours', 220);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_max_ADP_5', 'Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)', 221);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_max_ADP_20', 'Chronolog Plavix maintenance dose  whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)', 222);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_max_aa', 'Chronolog Plavix maintenance dose  whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)', 223);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_max_collagen1', 'Chronolog Plavix maintenance dose  whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)', 224);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_lag_ADP_5', 'Chronolog Plavix maintenance dose  whole blood lag time of ADP 5 µM in seconds', 'number', 'Assay result  (numeric)', 225);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_lag_ADP_20', 'Chronolog Plavix maintenance dose  whole blood lag time of ADP 20 µM in seconds', 'number', 'Assay result  (numeric)', 226);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_lag_aa', 'Chronolog Plavix maintenance dose  whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)', 227);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_standard_lag_collagen1', 'Chronolog Plavix maintenance dose  whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)', 228);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('VASP', 'VASP phosphorylation assay', 'number', 'VASP assay, please try to specify whether it''s after loading dose or maintenance dose. If not known, enter value here. Assay result  (numeric), expressed as a Platelet Reactivity Index (PRI) in % ', 229);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cyp2c19_genotypes', 'Cyp2C19 genotypes', 'string', 'Please specify for both alleles: *1, *2, *3, *4, *5, *6, *7, *8, *10,  *17', 230);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs4244285', 'CYP2C19*2 genotype G>A (rs4244285 )', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 231);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs4986893', 'CYP2C19*3 genotype G>A (rs4986893)', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 232);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs28399504', 'CYP2C19*4 genotype A>G (rs28399504)', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 233);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs56337013', 'CYP2C19*5 genotype C>T (rs56337013 )', 'string', 'C/C, C/T, T/T or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 234);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs72552267', 'CYP2C19*6 genotype G>A (rs72552267  )', 'string', 'A/A, A/G, G/G or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 235);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs72558186', 'CYP2C19*7 genotype T>A (rs72558186 )', 'string', 'T/T, T/A, A/A or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 236);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs12248560', 'CYP2C19*17  genotype C>T (rs12248560)', 'string', 'C/C, C/T, T/T or NA (Note all CYP2C19 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 237);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs662', 'PON1 genotype T>C (rs662)', 'string', 'C/C, C/T, T/T or NA (Note all PON1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 238);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs854560', 'PON1 genotype A>T (rs854560)', 'string', 'T/T, T/A, A/A or NA (Note all PON1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 239);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('rs1045642', 'ABCB1 genotype A>G (rs1045642)', 'string', 'A/A, A/G, G/G or NA (Note all ABCB1 genotypes to be reported on the coding strand, which is the complement of the hg18 reference strand)', 240);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_collagen_2', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 241);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_collagen_10', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 242);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_collagen_6', 'PAP-8 baseline platelet rich plasma max aggregation of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 243);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_epi', 'PAP-8 baseline platelet rich plasma max aggregation of Epi %', 'number', 'Assay result  (numeric)', 244);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_max_aa', 'PAP-8 baseline platelet rich plasma max aggregation of Arachadonic Acid %', 'number', 'Assay result  (numeric)', 245);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Addition_Pheno', 'Insert additional platelet aggregation phenotypes here', 'string', 'If you have additional type of platelet aggregation phenotypes, please add the columns here and let us know which columns you added', 246);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_lag_collagen_1', 'PAP-8 baseline platelet rich plasma lag time of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 247);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_lag_collagen_2', 'PAP-8 baseline platelet rich plasma lag time of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 248);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_lag_collagen_5', 'PAP-8 baseline platelet rich plasma lag time of Collagen 5 µg/ml%', 'number', 'Assay result  (numeric)', 249);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_baseline_lag_collagen_10', 'PAP-8 baseline platelet rich plasma lag time of Collagen 10 µg/ml%', 'number', 'Assay result  (numeric)', 250);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_ADP_2', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 2 µM %', 'number', 'Assay result  (numeric)', 251);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_ADP_5', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 5 µM %', 'number', 'Assay result  (numeric)', 252);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_ADP_10', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 10 µM %', 'number', 'Assay result  (numeric)', 253);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_ADP_20', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of ADP 20 µM %', 'number', 'Assay result  (numeric)', 254);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_collagen_1', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 1 µg/ml%', 'number', 'Assay result  (numeric)', 255);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('PAP_8_post_max_collagen_2', 'PAP-8 post Plavix loading dose platelet rich plasma max aggregation of Collagen 2 µg/ml%', 'number', 'Assay result  (numeric)', 256);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_max_collagen1', 'Chronolog baseline whole blood max aggregation of Collagen 1 µg/mlin ohms', 'number', 'Assay result  (numeric)', 257);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_lag_ADP_5', 'Chronolog baseline whole blood lag time of ADP 5 µg/mlin seconds', 'number', 'Assay result  (numeric)', 258);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_lag_ADP_20', 'Chronolog baseline whole blood lag time of ADP 20 µg/mlin seconds', 'number', 'Assay result  (numeric)', 259);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_lag_aa', 'Chronolog baseline whole blood lag time of Arachadonic Acid in seconds', 'number', 'Assay result  (numeric)', 260);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_baseline_lag_collagen1', 'Chronolog baseline whole blood lag time of Collagen 1 µg/mlin seconds', 'number', 'Assay result  (numeric)', 261);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_max_ADP_5', 'Chronolog Plavix post loading dose whole blood max aggregation of ADP 5 µM in ohms', 'number', 'Assay result  (numeric)', 262);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_max_ADP_20', 'Chronolog Plavix post loading dose whole blood max aggregation of ADP 20 µM in ohms', 'number', 'Assay result  (numeric)', 263);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('Chronolog_loading_max_aa', 'Chronolog Plavix post loading dose whole blood max aggregation of Arachadonic Acid in ohms', 'number', 'Assay result  (numeric)', 264);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('other_genotypes', 'Other Genotypes, please specify', 'string', 'please use HGNC gene symbols and RsIDs if available', 265);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cone_adp_surface_pct', 'Cone and Platelet Analyzer (ADP-induced surface coverage%)', 'number', null, 266);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cone_adp_surface_size', 'Cone and Platelet Analyzer (ADP-induced average size of platelet aggregates µm2)', 'number', null, 267);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('multiplate_aa_test', 'Multiplate AA test', 'number', null, 268);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('riken_plate_num', 'ICPC_RIKEN plate#', 'number', null, 269);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('riken_location_num', 'RIKEN location #', 'number', null, 270);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('riken_location', 'RIKEN location', 'string', null, 271);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('riken_new_id', 'New ID', 'number', null, 272);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('type_stent_thromb', 'type of stent thrombosis', 'string', 'definite=1, probable=2, possible=3, not available=NA', 273);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ative_malignancy', 'active malignancy', 'string', null, 274);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('stent_type', 'stent type', 'string', 'any DES=1,  BMS only=2, both= 3, balloon angioplasty with no stent implantation=0, not known=NA', 275);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cardio_cheock_pci', 'cardiogenic shock at the time of PCI', 'string', null, 276);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('hypercholesterolemia', 'Hypercholesterolemia', 'string', null, 277);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('peri_art_disease_base', 'peripheral arterial disease at baseline', 'string', null, 278);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('target_vessel_revasc', 'target vessel revascularization', 'string', null, 279);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ttf_target_vessel_revasc', 'time to the first target vessel revascularization', 'string', null, 280);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('nontarget_vessel_revasc', 'Non-target vessel revascularization', 'string', null, 281);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ttf_nontarget_vessel_revasc', 'time to the first non-target vessel revascularization', 'string', null, 282);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('hemorrhargic_stroke', 'hemorrhargic stroke', 'string', null, 283);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('ttf_hemorrhargic_stroke', 'Time to the first hemorrhargic stroke', 'string', null, 284);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('non_hs_crp', 'non-hs-CRP (mg/L)', 'number', 'numerical', 285);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cyp2c19_star2_count', 'Cyp2C19 genotypes number of *2', 'number', 'Number of *2 alleles', 286);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('cyp2c19_star17_count', 'Cyp2C19 genotypes number of *17', 'number', 'Number of *17 alleles', 287);
INSERT INTO propertynames (name, descrip, datatype, format, idx) VALUES ('levf_category', 'LEVF Category', 'number', '0: normale LVEF >55%, 1: slightly impaired LVEF 45-55%, 2: moderately impaired LVEF 35-44%, 3: severyl impaired LVEF <35%', 288);
commit;
