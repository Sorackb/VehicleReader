package org.lucassouza.vehiclereader.controller;

import org.lucassouza.vehiclereader.model.businessrule.ReferenceBR;
import org.lucassouza.vehiclereader.model.businessrule.YearPriceBR;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Reader extends Thread implements Communicable {

  private final Communicable observer;

  public Reader(Communicable observer) {
    this.observer = observer;
  }

  private void evaluate() {
    YearPriceBR yearPriceBR;
    ReferenceBR referenceBR;
    YearPrice lastYearPrice;

    yearPriceBR = new YearPriceBR();
    referenceBR = new ReferenceBR();
    
    referenceBR.communicateInterest(this.observer);
    lastYearPrice = yearPriceBR.searchLast();
    referenceBR.setLast(lastYearPrice);
    referenceBR.readAll();
  }

  @Override
  public void run() {
    this.evaluate();
  }

  @Override
  public void informAmount(ResourceType resourceType, Integer amount) {
    if (this.observer != null) {
      this.observer.informAmount(resourceType, amount);
    }
  }

  @Override
  public void informIncrement(ResourceType resourceType) {
    if (this.observer != null) {
      this.observer.informIncrement(resourceType);
    }
  }
}
