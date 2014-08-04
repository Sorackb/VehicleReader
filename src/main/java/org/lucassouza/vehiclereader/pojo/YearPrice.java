package org.lucassouza.vehiclereader.pojo;

import java.util.Date;
import org.lucassouza.vehiclereader.type.FuelType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class YearPrice {

  private Integer id;
  private Model model;
  private Reference reference;
  private Integer year;
  private Float price;
  private FuelType fuelType;
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
