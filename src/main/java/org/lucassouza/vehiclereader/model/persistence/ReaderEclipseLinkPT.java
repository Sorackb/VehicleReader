package org.lucassouza.vehiclereader.model.persistence;

import java.util.HashMap;
import java.util.List;
import org.lucassouza.dao.EclipseLinkPT;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 * @param <A>
 */
public class ReaderEclipseLinkPT<A> extends EclipseLinkPT<A> {

  public ReaderEclipseLinkPT(String persistenceUnitName,
          HashMap<String, String> properties) {
    super(persistenceUnitName, properties);
  }

  @Override
  public void create(List<A> objectList) {
    try {
      if (this.entityManager.getTransaction().isActive()) {
        Thread.sleep(50);
        this.create(objectList);
      } else {
        super.create(objectList);
      }
    } catch (InterruptedException | IllegalStateException ex) {
      try {
        Thread.sleep(50);
        this.create(objectList);
      } catch (InterruptedException ie) {
      }
    }
  }
}
