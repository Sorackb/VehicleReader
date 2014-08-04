package org.lucassouza.vehiclereader.controller;

import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.businessrule.YearPriceBR;
import org.lucassouza.vehiclereader.pojo.YearPrice;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Reader {
  private final Communicable observer;

  public Reader(Communicable observer) {
    this.observer = observer;
  }
  
  private void evaluate() {
    Interaction interaction;
    YearPriceBR yearPriceBR;
    YearPrice lastYearPrice;
  }
}
