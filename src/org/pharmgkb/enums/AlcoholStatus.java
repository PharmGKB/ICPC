package org.pharmgkb.enums;

import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;

/**
 * Alcohol Status enum
 *
 * @author Ryan Whaley
 */
public enum AlcoholStatus implements ExtendedEnum {

  NONE(0, "0", "none"),
  INFREQUENT(1, "1", "infrequent"),
  MODERATE(2, "2", "moderate"),
  FREQUENT(3, "3", "frequent"),
  UNKNOWN(99, "99", "unknown");

  private static ExtendedEnumHelper<AlcoholStatus> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;

  AlcoholStatus(int id, String shortName, String displayName) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<AlcoholStatus>();
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


  /**
   * Looks for the enum with the given Id.
   */
  public static AlcoholStatus lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static AlcoholStatus lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<AlcoholStatus> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<AlcoholStatus> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }

  }
