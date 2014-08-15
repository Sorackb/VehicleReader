package org.lucassouza.vehiclereader.model.persistence;

import org.lucassouza.dao.EclipseLinkPT;
import org.lucassouza.vehiclereader.pojo.Model;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ModelPT extends EclipseLinkPT<Model> {

  public ModelPT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = Model.class;
  }
}
