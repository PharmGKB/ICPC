package cl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.SubjectIterator;
import org.pharmgkb.enums.Property;
import org.pharmgkb.enums.Value;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ryan Whaley
 */
public class LoadSubjectProperties {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) {
    HibernateUtils.init();

    Session session = null;
    try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
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
    }
    catch (Exception ex) {
      sf_logger.error("Error updating samples", ex);
    }
    finally {
      HibernateUtils.close(session);
    }

    Global.shutdown();
    System.exit(0);
  }
}
