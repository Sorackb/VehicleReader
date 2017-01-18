package org.lucassouza.vehiclereader.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Customizer;
import org.lucassouza.vehiclereader.model.customizer.ModelCT;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
@Entity
@Table(name = "fipe.tb_model")
@Customizer(ModelCT.class)
public class Model implements Serializable {

  @Id
  private int id;
  private String description;
  @ManyToOne
  @JoinColumn(name = "id_brand")
  private Brand brand;
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "id_vehicle_classification")
  private VehicleClassification vehicleClassification;
  private static final long serialVersionUID = 1;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
    return model != null && model.getId() == this.getId();
  }
}
