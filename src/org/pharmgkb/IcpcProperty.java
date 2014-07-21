package org.pharmgkb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This model class represents a possible property that can be set for a Sample. This is seen as a column in the Excel
 * data template files.
 *
 * @author Ryan Whaley
 */
@Entity
@Table(name="propertyNames")
public class IcpcProperty {
  private String m_name;
  private String m_description;
  private String m_type;
  private Integer m_index;

  /**
   * The name of the property, short and with no spaces (more like a "key")
   */
  @Id
  @Column(name="name")
  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  /**
   * The longer, full-text description of the property
   */
  @Column(name="descrip")
  public String getDescription() {
    return m_description;
  }

  public void setDescription(String description) {
    m_description = description;
  }

  /**
   * The data type of the property, possible values being "string" or "number"
   */
  @Column(name="datatype")
  public String getType() {
    return m_type;
  }

  public void setType(String type) {
    m_type = type;
  }

  /**
   * A unique index number assigned on the DB level, defined in order of addition to the DB. This property has no
   * intrinsic value, only used for crude sorting.
   */
  @Column(name="idx")
  public Integer getIndex() {
    return m_index;
  }

  public void setIndex(Integer index) {
    m_index = index;
  }
}
