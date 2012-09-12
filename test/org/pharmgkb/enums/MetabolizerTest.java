package org.pharmgkb.enums;

import com.google.common.collect.Lists;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 9/10/12
 */
public class MetabolizerTest extends TestCase {

  public void testLookupByGenotypes() {
    Metabolizer metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*1","*1"));
    assertEquals(Metabolizer.EM, metab);

    metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*1","*17"));
    assertEquals(Metabolizer.UM, metab);

    metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*17","*1"));
    assertEquals(Metabolizer.UM, metab);

    metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*3","*17"));
    assertEquals(Metabolizer.IM, metab);

    metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*4","*5"));
    assertEquals(Metabolizer.PM, metab);

    metab = Metabolizer.lookupByGenotypes(Lists.newArrayList("*17","*17"));
    assertEquals(Metabolizer.UM, metab);

  }

}
