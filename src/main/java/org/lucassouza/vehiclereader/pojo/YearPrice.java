package org.lucassouza.vehiclereader.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.eclipse.persistence.annotations.Customizer;
import org.lucassouza.vehiclereader.model.customizer.YearPriceCT;
import org.lucassouza.vehiclereader.type.FuelType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */

@Entity
@Table(name = "fipe.year_price")
@Customizer(YearPriceCT.class)
public class YearPrice implements Serializable {

  @Id
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "id_model")
  private Model model;
  @ManyToOne
  @JoinColumn(name = "id_reference")
  private Reference reference;
  private Integer year;
  private Float price;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "id_fuel_type")
  private FuelType fuelType;
  @Temporal(javax.persistence.TemporalType.DATE)
  @Column(name = "reading_date")
  private Date readingDate;
  private static final long serialVersionUID = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public Reference getReference() {
    return reference;
  }

  public void setReference(Reference reference) {
    this.reference = reference;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public FuelType getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelType fuelType) {
    this.fuelType = fuelType;
  }

  public Date getReadingDate() {
    return readingDate;
  }

  public void setReadingDate(Date readingDate) {
    this.readingDate = readingDate;
  }

  public Boolean equals(YearPrice yearPrice) {
    return yearPrice != null && yearPrice.getId().equals(this.getId());
  }
}
