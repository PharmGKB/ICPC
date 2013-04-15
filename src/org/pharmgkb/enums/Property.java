package org.pharmgkb.enums;

import org.pharmgkb.util.ExtendedEnum;
import org.pharmgkb.util.ExtendedEnumHelper;

import java.util.Collection;

/**
 * @author Ryan Whaley
 */
public enum Property implements ExtendedEnum {
  SUBJECT_ID(0, "Subject_ID", "PharmGKB Subject ID", false),
  GENOTYPING(1, "Genotyping", "Genetic Sample Available for GWAS & Genotyping QC (> 2 �g)", true),
  PHENOTYPING(2, "Phenotyping", "Tissue Sample(s) Available for additional Phenotyping or QC", true),
  SAMPLE_SOURCE(3, "Sample_Source", "Tissue Sample Source  Available for Phenotyping and/or QC", true),
  PROJECT(4, "Project", "Project Site", true),
  GENDER(5, "Gender", "Gender", true),
  RACE_SELF(6, "Race_self", "Race (self-reported) ", false),
  RACE_OMB(7, "Race_OMB", "Race (OMB)", false),
  ETHNICITY_REPORTED(8, "Ethnicity_reported", "Ethnicity (Reported)  (not required for minimal dataset)", false),
  ETHNICITY_OMB(9, "Ethnicity_OMB", "Ethnicity (OMB)", false),
  COUNTRY(10, "Country", "Country of Origin", true),
  AGE(11, "Age", "Age", true)
  ;

  private static ExtendedEnumHelper<Property> s_extendedEnumHelper;
  private int m_id;
  private String m_shortName;
  private String m_displayName;
  private Boolean m_shownInReport;

  Property(int id, String shortName, String displayName, boolean shownInReport) {
    m_id = id;
    m_shortName = shortName;
    m_displayName = displayName;
    m_shownInReport = shownInReport;
    init();
  }

  private synchronized void init() {
    if (s_extendedEnumHelper == null) {
      s_extendedEnumHelper = new ExtendedEnumHelper<Property>();
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
  public static Property lookupById(int id) {

    return s_extendedEnumHelper.lookupById(id);
  }

  /**
   * Looks for the enum with the given name.
   */
  public static Property lookupByName(String text) {

    return s_extendedEnumHelper.lookupByName(text);
  }


  /**
   * Gets all the enums sorted by Id.
   */
  public static Collection<Property> getAllSortedById() {

    return s_extendedEnumHelper.getAllSortedById();
  }

  /**
   * Gets all the enums sorted by name.
   */
  public static Collection<Property> getAllSortedByName() {

    return s_extendedEnumHelper.getAllSortedByName();
  }


  @Override
  public final String toString() {

    return getDisplayName();
  }

  public Boolean isShownInReport() {
    return m_shownInReport;
  }

  public void setShownInReport(Boolean shownInReport) {
    m_shownInReport = shownInReport;
  }
}
