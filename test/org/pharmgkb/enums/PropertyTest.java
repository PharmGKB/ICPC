package org.pharmgkb.enums;

import org.junit.Test;
import org.pharmgkb.exception.PgkbException;

import static org.junit.Assert.*;

/**
 * @author Ryan Whaley
 */
public class PropertyTest {

  @Test
  public void testValidate() {
    assertTrue(Property.SUBJECT_ID.validate("PA12345"));
    assertTrue(Property.SUBJECT_ID.validate("PA12345,"));
    assertFalse(Property.SUBJECT_ID.validate("pa1234"));

    assertTrue(Property.GENOTYPING.validate("1"));
    assertTrue(Property.GENOTYPING.validate("Yes"));
    assertTrue(Property.GENOTYPING.validate("0"));
    assertTrue(Property.GENOTYPING.validate("no"));
    assertFalse(Property.GENOTYPING.validate("blah"));

    assertTrue(Property.PHENOTYPING.validate("0"));
    assertFalse(Property.PHENOTYPING.validate("99"));

    assertTrue(Property.PROJECT.validate("31"));
    assertFalse(Property.PROJECT.validate("project31"));

    assertTrue(Property.PROJECT.validate("31"));
    assertFalse(Property.PROJECT.validate("project31"));

    assertTrue(Property.GENDER.validate("male"));
    assertFalse(Property.GENDER.validate("blah"));

    assertTrue(Property.RACE_SELF.validate("blah"));

    assertTrue(Property.SAMPLE_SOURCE.validate("1;2;4"));
    assertFalse(Property.SAMPLE_SOURCE.validate("1;2;9"));
    assertFalse(Property.SAMPLE_SOURCE.validate("1;29"));
    assertTrue(Property.SAMPLE_SOURCE.validate("3"));

    assertTrue(Property.RACE_OMB.validate("Caucasian"));
    assertTrue(Property.RACE_OMB.validate("Asian"));
    assertTrue(Property.RACE_OMB.validate("white"));
    assertTrue(Property.RACE_OMB.validate("black"));
    assertTrue(Property.RACE_OMB.validate("Not Determined"));

    assertTrue(Property.DOSE_CLOPIDOGREL.validate("75 mg/day"));
    assertTrue(Property.DOSE_ASPIRIN.validate("75 mg/day"));

    assertTrue(Property.TIME_LOADING_CHRONOLOG.validate("12:34:56"));
    assertTrue(Property.TIME_LOADING_CHRONOLOG.validate("2:34:56"));
    assertTrue(Property.TIME_LOADING_CHRONOLOG.validate("1.2"));
    assertFalse(Property.TIME_LOADING_CHRONOLOG.validate("34blah"));
  }

  @Test
  public void testNormalize() throws PgkbException {
    assertEquals("PA1234", Property.SUBJECT_ID.normalize("PA1234,"));

    assertEquals("A/A", Property.RS1045642.normalize("aa"));
    assertEquals("A/T", Property.RS1045642.normalize("a/t"));

    assertEquals("white", Property.RACE_OMB.normalize("Caucasian"));
    assertEquals("NA", Property.RACE_OMB.normalize("Not Determined"));
    assertEquals("caucasian", Property.RACE_SELF.normalize("white"));

    assertEquals("1;2", Property.SAMPLE_SOURCE.normalize("Serum;Plasma"));

    assertEquals("1", Property.GENDER.normalize("male"));

    assertEquals("yes", Property.GENOTYPING.normalize("yes"));
    assertEquals("yes", Property.GENOTYPING.normalize("Yes"));
    assertEquals("yes", Property.GENOTYPING.normalize("1"));
    assertEquals("no", Property.GENOTYPING.normalize("no"));
    assertEquals("no", Property.GENOTYPING.normalize("0"));

    assertEquals("100", Property.DOSE_CLOPIDOGREL.normalize("100 mg/day"));
    assertEquals("100", Property.DOSE_ASPIRIN.normalize("100 mg/day"));

    assertEquals("165", Property.HEIGHT.normalize("1.65"));
    assertEquals("180", Property.HEIGHT.normalize("1.8"));
  }

}
