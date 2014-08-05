package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.YearPricePT;
import org.lucassouza.vehiclereader.pojo.Model;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.FuelType;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class YearPriceBR extends BasicBR {

  private final Interaction interaction;
  private final YearPricePT yearPricePT;
  private YearPrice lastYearPrice;
  private Boolean proceed;

  public YearPriceBR(Interaction interaction, Communicable observer) {
    this.observer = observer;
    this.resourceType = ResourceType.YEAR_PRICE;
    this.interaction = interaction;
    this.yearPricePT = new YearPricePT();
    this.proceed = true;
  }

  public List<YearPrice> readAll(Reference reference, Model model) {
    Elements yearPriceList = this.interaction.getPageSource().select(
            "select#ddlAnoValor > option:not(:nth-of-type(1))");
    List<YearPrice> result = new ArrayList<>();
    String id;
    YearPrice yearPrice;

    this.informAmount(yearPriceList.size());

    for (Element yearPriceElement : yearPriceList) {
      id = yearPriceElement.attr("value");

      if (this.proceed) {
        this.interaction.setYearPriceId(Integer.parseInt(id));
        yearPrice = this.convert(reference, model);
        result.add(yearPrice);
      }

      /* Neste caso a verificação de continuidade fica depois porque se o último
       * for igual ao atual não há necessidade de ler
       */
      if (!this.proceed && this.lastYearPrice.getId().equals(Integer.parseInt(id))) {
        this.proceed = true;
      }

      this.informIncrement();
    }

    // Tentativa de acelerar o processo, não necessitando que a conexão seja aberta várias vezes
    this.yearPricePT.create(result);

    return result;
  }

  private YearPrice convert(Reference reference, Model model) {
    Integer id = Integer.parseInt(this.interaction.getPageSource().select(
            "select#ddlAnoValor > option[selected=selected]").first().attr("value"));
    Float value = Float.parseFloat(this.interaction.getPageSource().select(
            "span#lblValor").first().text().replace("R$", "").replace(".", "")
            .replace(",", ".").trim());
    String description = this.interaction.getPageSource().select("span#lblAnoModelo").text();
    String descriptionPart = description.substring(0, 4);
    YearPrice result = new YearPrice();
    String fuel = null;
    Integer year;

    // Caso esteja escrito "Zero KM" o ano é o mesmo da referência
    if (descriptionPart.toUpperCase().equals("ZERO")) {
      year = reference.getYear();
      fuel = description.substring(9, description.length()).trim().toUpperCase();
    } else {
      year = Integer.parseInt(descriptionPart);
      if (description.length() > 4) {
        fuel = description.substring(5, description.length()).trim().toUpperCase();
      }
    }

    result.setId(id);
    result.setYear(year);
    result.setPrice(value);
    result.setModel(model);
    result.setReference(reference);
    result.setFuelType(FuelType.getFuelType(fuel));

    return result;
  }

  public YearPrice searchLast() {
    return this.yearPricePT.searchLast();
  }

  public void setLastYearPrice(YearPrice lastYearPrice) {
    if (lastYearPrice != null) {
      this.proceed = false;
    }

    this.lastYearPrice = lastYearPrice;
  }
}
