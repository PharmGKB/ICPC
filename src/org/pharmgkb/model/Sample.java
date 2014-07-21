package org.pharmgkb.model;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;
import org.pharmgkb.enums.*;
import org.pharmgkb.util.IcpcUtils;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * Class to represent a single sample of data. Some properties are set here in the model but most are defined in the
 * generic <code>properties</code> property.
 *
 * @author Ryan Whaley
 */
@Entity
@Table(name="samples")
public class Sample {
  private static Logger sf_logger = Logger.getLogger(Sample.class);

  private String m_subjectId;
  private Enum m_Genotyping;
  private Enum m_Phenotyping;
  private Set<Enum> m_SampleSource;
  private Integer m_Project;
  private Enum m_Gender;
  private String m_Raceself;
  private String m_RaceOMB;
  private String m_Ethnicityreported;
  private String m_EthnicityOMB;
  private String m_Country;
  private Double m_Age;
  private Map<String,String> m_properties;

  @Id
  @Column(name="Subject_ID")
  public String getSubjectId() {
    return m_subjectId;
  }

  public void setSubjectId(String subjectId) {
    m_subjectId = subjectId;
  }

  public String toString() {
    return "Subject "+getSubjectId();
  }

  @Column(name="Genotyping")
  @Type(type="valueType")
  public Enum getGenotyping() {
    return m_Genotyping;
  }

  public void setGenotyping(Enum genotyping) {
    m_Genotyping = genotyping;
  }

  @Column(name="Phenotyping")
  @Type(type="valueType")
  public Enum getPhenotyping() {
    return m_Phenotyping;
  }

  public void setPhenotyping(Enum phenotyping) {
    m_Phenotyping = phenotyping;
  }

  @SuppressWarnings("JpaDataSourceORMInspection")
  @ElementCollection
  @JoinTable(name="sampleSources", joinColumns = @JoinColumn(name="subject_id"))
  @Column(name="source")
  @Type(type="sampleSourceType")
  public Set<Enum> getSampleSource() {
    return m_SampleSource;
  }

  public void setSampleSource(Set<Enum> sampleSource) {
    m_SampleSource = sampleSource;
  }

  public void addSampleSource(Enum sampleSource) {
    if (m_SampleSource == null) {
      m_SampleSource = Sets.newHashSet();
    }
    m_SampleSource.add(sampleSource);
  }

  @Column(name="Project",nullable = false)
  public Integer getProject() {
    return m_Project;
  }

  public void setProject(Integer project) {
    m_Project = project;
  }

  @Column(name="Gender")
  @Type(type="genderType")
  public Enum getGender() {
    return m_Gender;
  }

  public void setGender(Enum gender) {
    m_Gender = gender;
  }

  @Column(name="Race_self")
  public String getRaceself() {
    return m_Raceself;
  }

  public void setRaceself(String raceself) {
    m_Raceself = raceself;
  }

  @Column(name="Race_OMB")
  public String getRaceOMB() {
    return m_RaceOMB;
  }

  public void setRaceOMB(String raceOMB) {
    m_RaceOMB = raceOMB;
  }

  public String calculateRace() {
    Race race = Race.lookupByFuzzyName(getRaceOMB(), getRaceself(), getEthnicityOMB(), getEthnicityreported());
    if (race==null) {
      return "NA";
    }
    else {
      return race.getShortName();
    }
  }

  @Column(name="Ethnicity_reported")
  public String getEthnicityreported() {
    return m_Ethnicityreported;
  }

  public void setEthnicityreported(String ethnicityreported) {
    m_Ethnicityreported = ethnicityreported;
  }

  @Column(name="Ethnicity_OMB")
  public String getEthnicityOMB() {
    return m_EthnicityOMB;
  }

  public void setEthnicityOMB(String ethnicityOMB) {
    m_EthnicityOMB = ethnicityOMB;
  }

  @Column(name="Country")
  public String getCountry() {
    return m_Country;
  }

  public void setCountry(String country) {
    m_Country = country;
  }

  @Column(name="Age")
  public Double getAge() {
    return m_Age;
  }

  public void setAge(Double age) {
    m_Age = age;
  }

  @ElementCollection
  @MapKeyClass(java.lang.String.class)
  @MapKeyColumn(name="datakey")
  @CollectionTable(name="sampleProperties",joinColumns = @JoinColumn(name="subjectId"))
  @Column(name="datavalue")
  public Map<String, String> getProperties() {
    return m_properties;
  }

  public void setProperties(Map<String, String> properties) {
    m_properties = properties;
  }

  /**
   * Gets the value for the {@link Property} in the <code>properties</code> collection.
   * @param property the {@link Property} to lookup
   * @return the value for the given {@link Property} for the given subject
   */
  public String getProperty(Property property) {
    if (m_properties != null && property != null) {
      String value = m_properties.get(property.getShortName());
      return IcpcUtils.isBlank(value) ? null : value;
    }
    return null;
  }

  /**
   * Add properties to this sample. This method will add the properties to the <code>properties</code> Map for this
   * {@link Sample} and will also assign the object properties in {@link Sample} that we want to strongly persist.
   * @param properties a Map of property shortNames to property values
   * @throws Exception can occur if no proper Source is part of the <code>properties</code> map
   */
  public void addProperties(Map<String, String> properties) throws Exception {
    if (m_properties == null) {
      m_properties = Maps.newHashMap();
    }
    m_properties.putAll(properties);

    // pick out all properties that we want to strongly persist for analysis
    String subjectId = getProperties().get(Property.SUBJECT_ID.getShortName());
    subjectId = StringUtils.strip(subjectId);
    subjectId = StringUtils.strip(subjectId,",");
    setSubjectId(subjectId);

    Value genotyping = Value.lookupByName(getProperties().get(Property.GENOTYPING.getShortName()));
    setGenotyping(genotyping);

    Value phenotyping = Value.lookupByName(getProperty(Property.PHENOTYPING));
    setPhenotyping(phenotyping);

    String sources = getProperty(Property.SAMPLE_SOURCE);
    if (!IcpcUtils.isBlank(sources)) {
      for (String value : Splitter.on(";").trimResults().split(sources)) {
        SampleSource source = SampleSource.lookupByName(value);
        if (source == null) {
          sf_logger.warn("can't find source for : "+value);
          throw new Exception("subject "+getSubjectId()+" can't find source for : "+value);
        }
        else {
          addSampleSource(SampleSource.lookupByName(value));
        }
      }
    }

    setProject(Integer.valueOf(getProperty(Property.PROJECT)));

    String genderString = getProperty(Property.GENDER);
    setGender(Gender.lookupByName(genderString));

    if ((getProperty(Property.RACE_SELF) != null
            && (getProperty(Property.RACE_SELF).equalsIgnoreCase("caucasian") || getProperty(Property.RACE_SELF).equalsIgnoreCase("white"))
        )
        ||
        (getProperty(Property.RACE_OMB) != null
            && (getProperty(Property.RACE_OMB).equalsIgnoreCase("caucasian") || getProperty(Property.RACE_OMB).equalsIgnoreCase("white"))
        )) {
      m_properties.put(Property.RACE_SELF.getShortName(), "caucasian");
      m_properties.put(Property.RACE_OMB.getShortName(), "white");
    }
    setRaceself(getProperty(Property.RACE_SELF));
    setRaceOMB(getProperty(Property.RACE_OMB));

    setEthnicityreported(getProperty(Property.ETHNICITY_REPORTED));
    setEthnicityOMB(getProperty(Property.ETHNICITY_OMB));

    setCountry(getProperty(Property.COUNTRY));

    if (getProperty(Property.AGE) != null) {
      setAge(Double.valueOf(getProperty(Property.AGE)));
    }
    else {
      sf_logger.warn("no age specified for "+getProject()+":"+getSubjectId());
    }
  }
}
