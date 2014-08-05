package org.lucassouza.vehiclereader.model.persistence;

import org.lucassouza.dao.EclipseLinkPT;
import org.lucassouza.vehiclereader.pojo.Brand;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BrandPT extends EclipseLinkPT<Brand> {

  public BrandPT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = Brand.class;
  }
}
