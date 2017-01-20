package org.lucassouza.vehiclereader.type;

/**
 *
 * @author Lucas Bernardo [sorackb@gmail.com]
 */
public enum FuelType {

  NONE(0), GASOLINE(1), DIESEL(2), ALCOHOL(3), UNIDENTIFIED(4), OTHER(5);
  private final Integer idDB;

  // Private porque são todas constantes instanciadas dentro da própria classe
  private FuelType(Integer aIdDB) {
    idDB = aIdDB;
  }

  public static FuelType getFuelType(String fuelTypeDescription) {
    FuelType result;

    if (fuelTypeDescription == null || fuelTypeDescription.trim().isEmpty()) {
      result = UNIDENTIFIED;
    } else {
      switch (fuelTypeDescription.trim().toUpperCase()) {
        case "ÁLCOOL":
        case "ALCOOL":
          result = ALCOHOL;
          break;
        case "GASOLINA":
          result = GASOLINE;
          break;
        case "DIESEL":
          result = DIESEL;
          break;
        default:
          System.out.println(fuelTypeDescription.trim().toUpperCase());
          result = OTHER;
      }
    }

    return result;
  }

  public Integer getIdDB() {
    return idDB;
  }
}
