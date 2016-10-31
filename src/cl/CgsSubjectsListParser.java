package cl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.SubjectIterator;
import org.pharmgkb.enums.Property;
import org.pharmgkb.enums.Value;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reads through a list of subject IDs and marks them as being CGS subjects in the system.
 * 
 * @author Ryan Whaley
 */
public class CgsSubjectsListParser extends CommonParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final String sf_fileDescriptor = "A file of CGS subject IDs (PA#) separated by newlines (UTF-8)";

  public static void main(String[] args) {
    HibernateUtils.init();

    CgsSubjectsListParser loadSubjectProperties = new CgsSubjectsListParser();
    try {
      loadSubjectProperties.parseCommandLineArgs(args);
      loadSubjectProperties.parseData();
    }
    catch (Exception ex) {
      sf_logger.error("Error updating samples", ex);
    }

    Global.shutdown();
    System.exit(0);
  }

  @Override
  protected void parseData() throws PgkbException {
    Session session = null;
    try (BufferedReader br = new BufferedReader(new FileReader(getDataFile()))) {
      Set<String> subjectIdSet = br.lines()
          .map(StringUtils::stripToNull)
          .filter(i -> i != null)
          .collect(Collectors.toSet());

      session = HibernateUtils.getSession();
      //noinspection unchecked
      List<String> ids = session.createQuery("select s.subjectId from Sample s").list();

      for (String id : ids) {
        Sample sample = (Sample)session.get(Sample.class, id);
        if (sample != null) {
          if (subjectIdSet.contains(sample.getSubjectId())) {
            sample.addProperty(Property.CGS, Value.Yes.getShortName());
          }
          else {
            sample.addProperty(Property.CGS, Value.No.getShortName());
          }
          SubjectIterator.postProcess(sample);
        }
      }

      HibernateUtils.commit(session);

      sf_logger.info("Updated {} records", subjectIdSet.size());
    } catch (FileNotFoundException e) {
      throw new PgkbException("Couldn't find file"+getDataFile());
    } catch (IOException e) {
      throw new PgkbException("Error with file IO", e);
    } finally {
      HibernateUtils.close(session);
    }
  }

  @Override
  String getFileDescriptor() {
    return sf_fileDescriptor;
  }
}
