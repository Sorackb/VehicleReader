package org.lucassouza.vehiclereader.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Customizer;
import org.lucassouza.vehiclereader.model.customizer.ReferenceCT;
import org.lucassouza.vehiclereader.type.ReferenceSituation;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
@Entity
@Table(name = "fipe.reference")
@Customizer(ReferenceCT.class)
public class Reference implements Serializable {

  @Id
  private Integer id;
  @Column(name = "description_pt")
  private String description;
  private Integer year;
  private Integer month;
  @Column(name = "id_reference_situation")
  private int referenceSituation;
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
    return ReferenceSituation.valueOf(referenceSituation);
  }

  public void setReferenceSituation(ReferenceSituation referenceSituation) {
    this.referenceSituation = referenceSituation.getIdDB();
  }

  public Boolean equals(Reference reference) {
    return reference != null && reference.getId().equals(this.getId());
  }
}
