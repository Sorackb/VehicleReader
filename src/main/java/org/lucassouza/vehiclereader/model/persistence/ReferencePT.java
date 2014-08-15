package org.lucassouza.vehiclereader.model.persistence;

import org.lucassouza.dao.EclipseLinkPT;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferencePT extends EclipseLinkPT<Reference> {

  public ReferencePT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = Reference.class;
  }
}
