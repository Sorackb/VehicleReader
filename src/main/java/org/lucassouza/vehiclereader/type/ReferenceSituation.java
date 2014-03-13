/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lucassouza.vehiclereader.type;

/**
 *
 * @author Lucas Bernardo <sorackb@gmail.com>
 */
public enum ReferenceSituation {

  NONE(0), PENDING(1), INCOMPLETE(2), COMPLETE(3);
  private final Integer idDB;

  // Private because all the constants are instantiated inside the class
  private ReferenceSituation(Integer aIdDB) {
    idDB = aIdDB;
  }

  public Integer getIdDB() {
    return idDB;
  }
}
