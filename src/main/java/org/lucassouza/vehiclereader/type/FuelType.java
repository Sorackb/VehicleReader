/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    if (fuelTypeDescription == null || fuelTypeDescription.trim().equals("")) {
      result = UNIDENTIFIED;
    } else if (fuelTypeDescription.toUpperCase().equals("GASOLINA")) {
      result = GASOLINE;
    } else if (fuelTypeDescription.toUpperCase().equals("ÁLCOOL")
            || fuelTypeDescription.toUpperCase().equals("ALCOOL")) {
      result = ALCOHOL;
    } else if (fuelTypeDescription.toUpperCase().equals("DIESEL")) {
      result = DIESEL;
    } else {
      result = OTHER;
    }

    return result;
  }

  public Integer getCodigoBD() {
    return idDB;
  }
}
