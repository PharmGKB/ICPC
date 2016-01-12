package cl;

import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.SubjectIterator;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Refreshes all calculated columns for {@link Sample} objects
 *
 * @author Ryan Whaley
 */
public class SampleUpdater {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) {
    HibernateUtils.init();

    Session session = null;
    try {
      session = HibernateUtils.getSession();

      //noinspection unchecked
      List<String> subjectIds = session
          .createQuery("select s.subjectId from Sample s order by s.project, s.subjectId")
          .list();

      int project = 0;
      for (String id : subjectIds) {

        Sample sample = (Sample)session.get(Sample.class, id);

        if (project != sample.getProject()) {
          sf_logger.info("Updating project {}", sample.getProject());
          project = sample.getProject();
        }

        SubjectIterator.postProcess(sample);
      }

      HibernateUtils.commit(session);
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
