package org.pharmgkb.model;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pharmgkb.enums.Gender;
import org.pharmgkb.enums.Property;
import org.pharmgkb.enums.Value;
import org.pharmgkb.util.HibernateUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Ryan Whaley
 */
public class SampleTest {

  public static final String sf_subjectId = "PA00";
  Session session = null;

  @Before
  public void before() {
    HibernateUtils.init();
    session = HibernateUtils.getSession();
  }

  @Test
  public void test() {
    save();
    query();
    delete();
  }

  public void save() {
    Sample sample = new Sample();
    sample.setSubjectId(sf_subjectId);
    sample.setProject(100);
    sample.setGender(Gender.MALE);
    sample.setGenotyping(Value.Yes);
    sample.setPhenotyping(Value.Yes);
    sample.setAge(21d);
    sample.addProperty(Property.ADP, "100");
    session.save(sample);
    HibernateUtils.commit(session);
  }

  public void query() {
    Sample sample = (Sample)session.get(Sample.class, sf_subjectId);
    assertNotNull(sample);
    assertEquals(Value.Yes, sample.getGenotyping());
    assertEquals(Value.Yes, sample.getPhenotyping());
    assertEquals(Integer.valueOf(100), sample.getProject());
    assertEquals("100", sample.getProperties().get(Property.ADP));
  }

  public void delete() {
    Sample sample = (Sample)session.get(Sample.class, sf_subjectId);
    assertNotNull(sample);

    session.delete(sample);
    HibernateUtils.commit(session);
  }

  @After
  public void after() {
    HibernateUtils.close(session);
  }


}
