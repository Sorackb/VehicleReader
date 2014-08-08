package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public abstract class BasicBR {

  protected ResourceType resourceType;
  protected List<Communicable> observerList;
  protected YearPrice lastYearPrice;
  protected Boolean proceed;

  public void communicateInterest(Communicable newObserver) {
    if (this.observerList == null) {
      this.observerList = new ArrayList<>();
    }

    this.observerList.add(newObserver);
  }

  public void communicateInterest(List<Communicable> newObserverList) {
    this.observerList = newObserverList;
  }

  protected void informAmount(Integer amount) {
    if (this.observerList != null) {
      for (Communicable observer : this.observerList) {
        observer.informAmount(this.resourceType, amount);
      }
    }
  }

  protected void informIncrement() {
    if (this.observerList != null) {
      for (Communicable observer : this.observerList) {
        observer.informIncrement(this.resourceType);
      }
    }
  }

  public void setLast(YearPrice lastYearPrice) {
    this.lastYearPrice = lastYearPrice;

    if (lastYearPrice != null) {
      this.proceed = false;
    } 
  }
}
