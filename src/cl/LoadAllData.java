package cl;

import com.google.common.base.Preconditions;
import org.pharmgkb.CombinedDataReport;
import org.pharmgkb.DnaExcelParser;
import org.pharmgkb.RikenParser;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

/**
 * This class runs all data parsers in the correct order and then produces the combined data sheet.
 * 
 * This will also look for a properties file called <code>filepaths.properties</code> that contains filepaths to all 
 * the required data files and paths.
 * 
 * @author Ryan Whaley
 */
public class LoadAllData {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final String KEY_SUB_DIR        = "submissions.directory";
  private static final String KEY_DNA_FILE       = "dna.file";
  private static final String KEY_RIKEN_FILE     = "riken.file";
  private static final String KEY_PHENO_FILE     = "pheno.file";
  private static final String KEY_PHENO_ANL_FILE = "phe.analysis.file";
  private static final String KEY_CGS_GENO_FILE  = "cgs.genotype.file";
  private static final String KEY_CGS_SUBJ_FILE  = "cgs.subjects.file";
  private static final String KEY_OUTPUT_PATH    = "output.directory";

  private Properties m_properties; 
  
  public static void main(String[] args) {

    try {
      HibernateUtils.init();

      LoadAllData loadAllData = new LoadAllData(args);
      loadAllData.run();
          
    } catch (IOException e) {
      sf_logger.error("Couldn't run parsers");
      System.exit(1);
    } finally {
      HibernateUtils.shutdown();
    }
    System.exit(0);
    
  }
  
  private LoadAllData(String[] args) throws IOException {
    Preconditions.checkNotNull(args);
    Preconditions.checkArgument(args.length == 1);
    
    try (FileInputStream in = new FileInputStream(args[0])) {
      m_properties = new Properties();
      m_properties.load(in);
    }
  } 
  
  private void run() throws IOException {
    try {
      {
        TemplateParser templateParser = new TemplateParser();
        templateParser.setDataFile(new File((String) m_properties.get(KEY_SUB_DIR)));
        templateParser.parseData();
      }

      {
        DnaExcelParser dnaExcelParser = new DnaExcelParser(new File((String)m_properties.get(KEY_DNA_FILE)));
        dnaExcelParser.parse();
      }

      {
        RikenParser rikenParser = new RikenParser(new File((String)m_properties.get(KEY_RIKEN_FILE)));
        rikenParser.parse();
      }

      {
        PhenoParser phenoParser = new PhenoParser();
        phenoParser.setDataFile(new File((String)m_properties.get(KEY_PHENO_FILE)));
        phenoParser.parseData();
      }

      {
        PhenoAnalysisParser phenoAnalysisParser = new PhenoAnalysisParser();
        phenoAnalysisParser.setDataFile(new File((String)m_properties.get(KEY_PHENO_ANL_FILE)));
        phenoAnalysisParser.parseData();
      }

      {
        CgsGenotypeParser cgsGenotypeParser = new CgsGenotypeParser();
        cgsGenotypeParser.setDataFile(new File((String)m_properties.get(KEY_CGS_GENO_FILE)));
        cgsGenotypeParser.parseData();
      }

      {
        CgsSubjectsListParser cgsSubjectsListParser = new CgsSubjectsListParser();
        cgsSubjectsListParser.setDataFile(new File((String)m_properties.get(KEY_CGS_SUBJ_FILE)));
        cgsSubjectsListParser.parseData();
      }
      
      {
        CombinedDataReport report = new CombinedDataReport(new File((String)m_properties.get(KEY_OUTPUT_PATH)), null);
        report.generate();
      }
    } catch (Exception ex) {
      sf_logger.error("Error loading data", ex);
    }
  }
}
