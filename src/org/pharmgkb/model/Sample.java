package org.pharmgkb.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;
import org.pharmgkb.enums.*;

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
  private String m_subjectId;
  private Enum m_Genotyping;
  private Enum m_Phenotyping;
  private Set<SampleSource> m_SampleSource;
  private Integer m_Project;
  private Enum m_Gender;
  private String m_Raceself;
  private String m_RaceOMB;
  private String m_Ethnicityreported;
  private String m_EthnicityOMB;
  private String m_Country;
  private Double m_Age;
  private Map<Property,String> m_properties;

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
  public Set<SampleSource> getSampleSource() {
    return m_SampleSource;
  }

  public void setSampleSource(Set<SampleSource> sampleSource) {
    m_SampleSource = sampleSource;
  }

  public void addSampleSource(SampleSource sampleSource) {
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
  @MapKeyClass(Property.class)
  @MapKeyColumn(name="property_id")
  @CollectionTable(name="properties",joinColumns = @JoinColumn(name="subject_id"))
  @Column(name="value")
  public Map<Property, String> getProperties() {
    return m_properties;
  }

  public void setProperties(Map<Property, String> properties) {
    m_properties = properties;
  }

  public void addProperty(Property property, String value) {
    if (m_properties == null) {
      m_properties = Maps.newHashMap();
    }
    m_properties.put(property, value);
  }
}
