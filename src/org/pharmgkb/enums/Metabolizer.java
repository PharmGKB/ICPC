package org.pharmgkb.enums;

import com.google.common.base.Preconditions;
import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 9/10/12
 */
public enum Metabolizer implements ExtendedEnum {
  UM(0, "UM", "Ultrarapid Metabolizer"),
  EM(1, "EM", "Extensive Metabolizer"),
  IM(2, "IM", "Intermediate Metabolizer"),
  PM(3, "PM", "Poor Metabolizer"),
  Unknown(99, "Unknown", "Unknown");

  private static ExtendedEnumHelper<Metabolizer> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;

  Metabolizer(int id, String shortName, String displayName) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<Metabolizer>();
    }
    s_extendedEnumHelper.add(this, m_id, m_shortName, m_displayName);
  }

  /**
   * Gets the Id of this enum.
   */
  public int getId() {

    return m_id;
  }

  /**
   * Gets the short name of this enum.
   */
  public String getShortName() {

    return m_shortName;
  }

  /**
   * Gets the display name of this enum.  Will return short name if no display name is defined.
   */
  public String getDisplayName() {

    if (m_displayName != null) {
      return m_displayName;
    }
    return m_shortName;
  }

  public static Metabolizer lookupByGenotypes(List<String> genotypes) {
    Preconditions.checkNotNull(genotypes);
    Preconditions.checkArgument(genotypes.size()==2, "Must be 2 and only 2 genotypes");

    if (genotypes.get(0).equals("*1") && genotypes.get(1).equals("*1")) {
      return Metabolizer.EM;
    }
    else if (genotypes.get(0).equals("*17") && genotypes.get(1).equals("*17")) {
      return Metabolizer.UM;
    }
    else if (genotypes.contains("*1")) {
      if (genotypes.contains("*17")) {
        return Metabolizer.UM;
      }
      else {
        return Metabolizer.IM;
      }
    }
    else if (genotypes.contains("*17")) {
      return Metabolizer.IM;
    }
    else {
      return Metabolizer.PM;
    }
  }

    /**
    * Looks for the enum with the given Id.
    */
  public static Metabolizer lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static Metabolizer lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<Metabolizer> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<Metabolizer> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }

}
