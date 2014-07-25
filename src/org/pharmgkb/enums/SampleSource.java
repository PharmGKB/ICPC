package org.pharmgkb.enums;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Ryan Whaley
 */
public enum SampleSource implements ExtendedEnum {
  UNKNOWN(0, "0", "Unknown"),
  SERUM(1, "1", "Serum"),
  PLASMA(2, "2", "Plasma"),
  TISSUE(3, "3", "Tissue"),
  OTHERS(4, "4", "Others");

  private static ExtendedEnumHelper<SampleSource> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;

  SampleSource(int id, String shortName, String displayName) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<>();
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
  public static SampleSource lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static SampleSource lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<SampleSource> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<SampleSource> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }

  public static Pattern validationPattern() {
    Collection<SampleSource> sources = SampleSource.getAllSortedById();
    Set<String> tokens = Sets.newHashSet();
    for (SampleSource source : sources) {
      tokens.add(source.getShortName());
      tokens.add(source.getDisplayName());
    }
    return Pattern.compile("("+Joiner.on("|").join(tokens)+"|\\;)+");
  }

  public static Set<SampleSource> parseList(String list) throws PgkbException {
    Set<SampleSource> sources = Sets.newHashSet();

    if (StringUtils.isNotBlank(list)) {
      for (String token : Splitter.on(";").trimResults().split(list)) {
        SampleSource source = SampleSource.lookupByName(token);
        if (source == null) {
          throw new PgkbException("No sample source mapped for "+token);
        }
        else {
          sources.add(source);
        }
      }
    }

    return sources;
  }

  @Override
  public final String toString() {

    return getDisplayName();
  }

  }
