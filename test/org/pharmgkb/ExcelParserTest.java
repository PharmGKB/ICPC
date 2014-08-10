package org.pharmgkb;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;
import java.net.URL;

/**
 * @author Ryan Whaley
 */
public class ExcelParserTest {

  @Before
  public void before() {
    HibernateUtils.init();
  }

  @Test
  public void testLoadFormats() throws Exception {
    ExcelParser parser = new ExcelParser(new File(getClass().getResource("ICPC_Submission_Template.xlsx").toURI()));
    parser.loadFormats();
  }

}