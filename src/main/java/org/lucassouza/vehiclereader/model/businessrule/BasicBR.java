package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BasicBR {

  protected ResourceType resourceType;
  protected List<Communicable> observerList;
  protected Interaction interaction;

  public void communicateInterest(Communicable newObserver) {
    if (this.observerList == null) {
      this.observerList = new ArrayList<>();
    }

    this.observerList.add(newObserver);
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
}
