package org.pharmgkb.util;

import org.junit.Test;
import org.pharmgkb.enums.SampleSource;
import org.pharmgkb.exception.PgkbException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

}
