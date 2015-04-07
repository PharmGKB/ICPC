package cl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Parser to read phenotype data from the given CSV file.
 *
 * @author Ryan Whaley
 */
public class PhenoAnalysisParser extends CommonParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(PhenoAnalysisParser.class);
  private static final String sf_fileDescriptor = "Phenotype analysis file in CSV format, 3 columns";

  public static void main(String[] args) {
    HibernateUtils.init();
    PhenoAnalysisParser parser = new PhenoAnalysisParser();
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

  protected void parseData() throws PgkbException {
    sf_logger.info("Starting to parse");
    Session session = null;
    try (FileReader fr = new FileReader(getDataFile()); BufferedReader br = new BufferedReader(fr)) {
      session = HibernateUtils.getSession();

      // burn the first two lines
      br.readLine();br.readLine();

      int sampleSaved = 0;
      int sampleParsed = 0;
      String line;

      while ((line = br.readLine())!=null) {
        Iterable<String> fields = Splitter.on(",").split(line);
        List<String> fieldList = Lists.newArrayList(fields);

        String sampleId = fieldList.get(0).replaceAll("\"", "");
        String cascaded = fieldList.get(1).replaceAll("\"", "");
        String max      = fieldList.get(2).replaceAll("\"", "");

        if (StringUtils.isBlank(sampleId)) {
          continue;
        }
        sampleParsed++;

        Sample sample = (Sample)session.get(Sample.class, sampleId);
        if (sample == null) {
          sf_logger.warn("No sample found with ID: "+sampleId);
        }
        else {
          setProperty(sample, Property.CASCADED_STDADP_PHENOTYPE, cascaded);
          setProperty(sample, Property.STDADP_PHENOTYPE_MAX, max);
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

  private void setProperty(Sample sample, Property property, String value) throws PgkbException {
    if (property.validate(value)) {
      String normalizedValue = property.normalize(value);
      sample.addProperty(property, normalizedValue);
    } else {
      sf_logger.warn("Bad value for "+sample.getSubjectId()+" "+property+": "+value);
    }
  }

  public String getFileDescriptor() {
    return sf_fileDescriptor;
  }
}
