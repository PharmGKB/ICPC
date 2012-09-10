package org.pharmgkb.enums;

import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 9/4/12
 */
public enum DiabetesStatus implements ExtendedEnum {

  TYPE1(1, "1", "type 1"),
  TYPE2(2, "2", "type 2"),
  NONE(0, "0", "none"),
  UNKNOWN(99, "99", "unknown");

  private static ExtendedEnumHelper<DiabetesStatus> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;

  DiabetesStatus(int id, String shortName, String displayName) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<DiabetesStatus>();
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
  public static DiabetesStatus lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static DiabetesStatus lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<DiabetesStatus> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<DiabetesStatus> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }

  }
