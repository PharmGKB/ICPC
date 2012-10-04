package org.pharmgkb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 10/3/12
 */
@Entity
@Table(name="propertyNames")
public class IcpcProperty {
  private String m_name;
  private String m_description;
  private String m_type;
  private Integer m_index;

  @Id
  @Column(name="name")
  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  @Column(name="descrip")
  public String getDescription() {
    return m_description;
  }

  public void setDescription(String description) {
    m_description = description;
  }

  @Column(name="datatype")
  public String getType() {
    return m_type;
  }

  public void setType(String type) {
    m_type = type;
  }

  @Column(name="idx")
  public Integer getIndex() {
    return m_index;
  }

  public void setIndex(Integer index) {
    m_index = index;
  }
}
