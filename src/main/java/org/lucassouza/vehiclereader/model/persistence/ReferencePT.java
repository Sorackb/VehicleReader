package org.lucassouza.vehiclereader.model.persistence;

import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferencePT extends ReaderEclipseLinkPT<Reference> {

  public ReferencePT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = Reference.class;
  }
}
