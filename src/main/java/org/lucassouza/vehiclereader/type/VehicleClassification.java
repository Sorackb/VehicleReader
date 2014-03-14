/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lucassouza.vehiclereader.type;

/**
 *
 * @author Lucas Souza <lucas.souza@virtual.inf.br>
 */
public enum VehicleClassification {

  NONE(0, 0, ""), CAR(1, 51, "p"), MOTORCYCLE(2, 52, "m"), TRUCK(3, 53, "c");
  private final Integer idDB;
  private final Integer id;
  private final String complement;

  // Private because all the constants are instantiated inside the class
  private VehicleClassification(Integer aIdDB, Integer aId, String aComplement) {
    idDB = aIdDB;
    id = aId;
    complement = aComplement;
  }

  public Integer getId() {
    return id;
  }

  public Integer getIdDB() {
    return idDB;
  }

  public String getComplement() {
    return complement;
  }
}