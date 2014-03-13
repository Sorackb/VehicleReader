package org.lucassouza.vehiclereader.pojo;

import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza <sorackb@gmail.com>
 */
public class Model {

  private String id;
  private String description;
  private Brand brand;
  private VehicleClassification vehicleClassification;
  private static final long serialVersionUID = 1;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public VehicleClassification getVehicleClassification() {
    return vehicleClassification;
  }

  public void setVehicleClassification(VehicleClassification vehicleClassification) {
    this.vehicleClassification = vehicleClassification;
  }

  public Boolean equals(Model model) {
    return model != null && model.getId().equals(this.getId());
  }
}
