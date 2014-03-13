package org.lucassouza.vehiclereader.pojo;

import org.lucassouza.vehiclereader.type.ReferenceSituation;

/**
 *
 * @author Lucas Souza <sorackb@gmail.com>
 */
public class Reference {

  private Integer id;
  private String description;
  private Integer year;
  private Integer month;
  private ReferenceSituation referenceSituation;
  private static final long serialVersionUID = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public ReferenceSituation getReferenceSituation() {
    return referenceSituation;
  }

  public void setReferenceSituation(ReferenceSituation referenceSituation) {
    this.referenceSituation = referenceSituation;
  }

  public Boolean equals(Reference reference) {
    return reference != null && reference.getId().equals(this.getId());
  }
}
