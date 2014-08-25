package org.pharmgkb.enums;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Race descriptors as determined by the OMB.
 *
 * @author Ryan Whaley
 */
public enum Race implements ExtendedEnum {
  WHITE(0, "white", "White"),
  BLACK(1, "black", "Black"),
  ASIAN(2, "asian", "Asian"),
  HISPANIC(4, "hispanic", "Hispanic"),
  AMERICAN_INDIAN(5, "americanIndian", "American Indian or Alaskan Native"),
  OTHER(3, "other", "Other"),
  ;

  private static ExtendedEnumHelper<Race> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;

  private static final Map<String,Race> sf_fuzzyMap = Maps.newHashMap();
  static {
    sf_fuzzyMap.put("caucasian", WHITE);
    sf_fuzzyMap.put("caucasien", WHITE);
    sf_fuzzyMap.put("asia", ASIAN);
    sf_fuzzyMap.put("african", BLACK);
    sf_fuzzyMap.put("han chinese", ASIAN);
    sf_fuzzyMap.put("korean", ASIAN);
    sf_fuzzyMap.put("others", OTHER);
  }

  Race(int id, String shortName, String displayName) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<Race>();
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
  public static Race lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static Race lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }

  /**
   * Returns a fuzzy match search for race that searches through near-matches (e.g. caucasian>white). Null if nothing
   * can be matched.
   * @param text a <code>text</code> description of race
   * @return the equivalent Race
   */
  public static Race lookupByFuzzyName(String text) {
    String cleanText = StringUtils.stripToNull(text);

    if (StringUtils.isBlank(cleanText)) {
      return null;
    }

    cleanText = StringUtils.lowerCase(cleanText);

    Race race = s_extendedEnumHelper.lookupByName(cleanText);
    if (race==null) {
      race = sf_fuzzyMap.get(cleanText);
    }

    return race;
  }

  /**
   * Will return the first matching Race for the given String races
   * @param races a collection of race Strings
   * @return the equivalent Race
   */
  public static Race lookupByFuzzyName(String... races) {
    for (String raceName : races) {
      Race race = lookupByFuzzyName(raceName);
      if (race!=null) {
        return race;
      }
    }
    return null;
  }

  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<Race> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<Race> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }

  public static Pattern validationPattern() {
    Collection<Race> races = Race.getAllSortedById();
    Set<String> tokens = Sets.newHashSet();
    for (Race race : races) {
      tokens.add(race.getShortName());
      tokens.add(race.getDisplayName());
    }
    for (String key : sf_fuzzyMap.keySet()) {
      tokens.add(key);
      tokens.add(StringUtils.capitalize(key));
    }
    return Pattern.compile("("+ Joiner.on("|").join(tokens)+"|\\;)+");
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }
}
