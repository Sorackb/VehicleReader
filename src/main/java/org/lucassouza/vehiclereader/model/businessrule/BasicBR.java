package org.lucassouza.vehiclereader.model.businessrule;

import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BasicBR {

  protected ResourceType resourceType;
  protected Communicable observer;
  protected Interaction interaction;

  protected void informAmount(Integer amount) {
    if (this.observer != null) {
      this.observer.informAmount(this.resourceType, amount);
    }
  }

  protected void informIncrement() {
    if (this.observer != null) {
      this.observer.informIncrement(this.resourceType);
    }
  }
}
