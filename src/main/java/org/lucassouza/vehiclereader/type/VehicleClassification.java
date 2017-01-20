package org.lucassouza.vehiclereader.type;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public enum VehicleClassification {

  NONE(0, 0), CAR(1, 1), MOTORCYCLE(2, 3), TRUCK(3, 2);
  private final Integer idDB;
  private final Integer id;

  // Private because all the constants are instantiated inside the class
  private VehicleClassification(Integer aIdDB, Integer aId) {
    idDB = aIdDB;
    id = aId;
  }

  public Integer getId() {
    return id;
  }

  public Integer getIdDB() {
    return idDB;
  }
}
