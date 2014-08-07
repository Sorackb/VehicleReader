package org.lucassouza.vehiclereader.model.persistence;

import java.util.LinkedHashMap;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class YearPricePT extends ReaderEclipseLinkPT<YearPrice> {

  public YearPricePT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = YearPrice.class;
  }

  public YearPrice searchLast() {
    LinkedHashMap<String, String> orderBy = new LinkedHashMap<>();
    YearPrice result;

    orderBy.put("readingDate", "desc");

    result = this.read(null, orderBy);

    return result;
  }
}
