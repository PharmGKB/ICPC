package org.pharmgkb.util;

import org.junit.Test;
import org.pharmgkb.enums.Property;
import org.pharmgkb.enums.SampleSource;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Test the utility methods in IcpcUtils
 *
 * @author Ryan Whaley
 */
public class IcpcUtilsTest {

  @Test
  public void testMakeEnumValidator() throws PgkbException {
    Pattern validator = IcpcUtils.makeEnumValidator(SampleSource.class);

    Matcher m = validator.matcher(SampleSource.SERUM.getShortName());
    assertTrue(m.matches());

    m = validator.matcher("wharf arts");
    assertTrue(!m.matches());
  }

  @Test
  public void testIsBlank() {
    assertTrue(IcpcUtils.isBlank(null));
    assertTrue(IcpcUtils.isBlank("NA"));
    assertTrue(IcpcUtils.isBlank("N/A"));
    assertTrue(IcpcUtils.isBlank("na"));
    assertTrue(IcpcUtils.isBlank("n/a"));
    assertTrue(IcpcUtils.isBlank("Not Available"));
    assertTrue(IcpcUtils.isBlank("not available"));
    assertTrue(IcpcUtils.isBlank("not known"));
    assertTrue(IcpcUtils.isBlank("unknown"));
    assertFalse(IcpcUtils.isBlank("yup"));
    assertFalse(IcpcUtils.isBlank("12345"));
    assertFalse(IcpcUtils.isBlank("/"));
  }

  @Test
  public void testCalculateBmi() throws PgkbException {
    Sample sample = new Sample();
    sample.addProperty(Property.WEIGHT, "70");
    sample.addProperty(Property.HEIGHT, "180");

    assertEquals("21.6", IcpcUtils.calculateBmi(sample));

    sample.addProperty(Property.BMI, "2.0");

    assertEquals("2.0", IcpcUtils.calculateBmi(sample));

    sample = new Sample();
    sample.addProperty(Property.WEIGHT, Property.WEIGHT.normalize("67"));
    sample.addProperty(Property.HEIGHT, Property.HEIGHT.normalize("0"));
    sample.addProperty(Property.BMI, Property.HEIGHT.normalize("0.0"));

    assertEquals(IcpcUtils.NA, IcpcUtils.calculateBmi(sample));
  }

  @Test
  public void testConvertCreatinine() {
    Sample sample = new Sample();
    sample.setProject(7);
    sample.addProperty(Property.CREATININE, "88.4");

    assertEquals("1.00", IcpcUtils.convertCreatinine(sample));

    sample.setProject(1);

    assertEquals("88.4", IcpcUtils.convertCreatinine(sample));
  }

}
