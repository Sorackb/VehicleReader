package org.lucassouza.vehiclereader.model.persistence;

import java.util.LinkedHashMap;
import org.lucassouza.vehiclereader.pojo.YearPrice;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class YearPricePT extends BasicPT<YearPrice> {

  public YearPricePT() {
    super();
    this.referencedClass = YearPrice.class;
  }

  public YearPrice searchLast() {
    LinkedHashMap<String, String> orderBy = new LinkedHashMap<>();
    YearPrice result;

    orderBy.put("readingDate", "desc");

    result = this.search(null, orderBy);

    return result;
  }
}
