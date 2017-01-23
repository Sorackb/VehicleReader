package org.lucassouza.vehiclereader.type;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public enum VehicleClassification {

  NONE(0), CAR(1), MOTORCYCLE(2), TRUCK(3);
  private final Integer id;

  // Private because all the constants are instantiated inside the class
  private VehicleClassification(Integer id) {
    this.id = id;
  }

  public static VehicleClassification valueOf(Integer id) {
    for (VehicleClassification vechicleClassification : VehicleClassification.values()) {
      if (vechicleClassification.getId().equals(id)) {
        return vechicleClassification;
      }
    }

    return null;
  }

  public Integer getId() {
    return id;
  }
}
