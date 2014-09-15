package cl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Parser to read phenotype data from the given CSV file.
 *
 * @author Ryan Whaley
 */
public class PhenoParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(TemplateParser.class);
  private File m_dataFile;

  public static void main(String[] args) {
    HibernateUtils.init();
    PhenoParser parser = new PhenoParser();
    try {
      parser.parseCommandLineArgs(args);
      parser.parseData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    Global.shutdown();
    System.exit(0);
  }

  private void parseData() throws PgkbException {
    sf_logger.info("Starting to parse");
    Session session = null;
    try (FileReader fr = new FileReader(getDataFile()); BufferedReader br = new BufferedReader(fr)) {
      session = HibernateUtils.getSession();

      int sampleSaved = 0;
      int sampleParsed = 0;
      String line;
      while ((line = br.readLine())!=null) {
        Iterable<String> fields = Splitter.on(",").split(line);
        List<String> fieldList = Lists.newArrayList(fields);

        if (StringUtils.isBlank(fieldList.get(0).replaceAll("\"",""))) {
          continue;
        }
        sampleParsed++;

        String sampleId = fieldList.get(1).replaceAll("\"", "");
        String rawPheno = fieldList.get(16).replaceAll("\"", "");
        String stdPheno = fieldList.get(17).replaceAll("\"", "");

        Sample sample = (Sample)session.get(Sample.class, sampleId);
        if (sample == null) {
          sf_logger.warn("No sample found with ID: "+sampleId);
        }
        else {
          if (Property.PHENO_RAW.validate(rawPheno)) {
            rawPheno = Property.PHENO_RAW.normalize(rawPheno);
            sample.addProperty(Property.PHENO_RAW, rawPheno);
          } else {
            sf_logger.warn("Bad value for "+sampleId+" raw_pheno: "+rawPheno);
          }

          if (Property.PHENO_STD.validate(stdPheno)) {
            stdPheno = Property.PHENO_STD.normalize(stdPheno);
            sample.addProperty(Property.PHENO_STD, stdPheno);
          } else {
            sf_logger.warn("Bad value for "+sampleId+" std_pheno: "+stdPheno);
          }

          sampleSaved++;
        }
      }
      HibernateUtils.commit(session);
      sf_logger.info("Samples saved: "+sampleSaved+"/"+sampleParsed);
    }
    catch (IOException e) {
      throw new PgkbException("Couldn't read file");
    }
    finally {
      HibernateUtils.close(session);
    }
  }

  private void parseCommandLineArgs(String args[]) throws Exception {
    CliHelper cliHelper = new CliHelper(getClass(), false);
    cliHelper.addOption("f", "file", "ICPC phenotype file to read", "pathToFile");

    try {
      cliHelper.parse(args);
      if (cliHelper.isHelpRequested()) {
        cliHelper.printHelp();
        System.exit(1);
      }
    } catch (Exception ex) {
      throw new Exception("Error parsing arguments", ex);
    }

    if (cliHelper.hasOption("-f")) {
      File templateFile = new File(cliHelper.getValue("-f"));
      if (templateFile.exists()) {
        setDataFile(templateFile);
      }
      else {
        throw new Exception("File not found "+cliHelper.getValue("-f"));
      }
    }
  }

  public File getDataFile() {
    return m_dataFile;
  }

  public void setDataFile(File m_dataFile) {
    this.m_dataFile = m_dataFile;
  }
}
