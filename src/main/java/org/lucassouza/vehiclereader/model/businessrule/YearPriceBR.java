package org.lucassouza.vehiclereader.model.businessrule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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

  private final YearPricePT yearPricePT;

  public YearPriceBR() {
    this.yearPricePT = new YearPricePT();
    this.resourceType = ResourceType.YEAR_PRICE;
  }

  public List<YearPrice> readAll(Reference reference,
          Model model) throws IOException {
    List<YearPrice> result = new ArrayList<>();
    JSONArray list;

    list = new JSONArray(Interaction.getInstance().getLastResponse().body());
    this.informAmount(list.length());

    for (Object object : list) {
      JSONObject converted = (JSONObject) object;
      YearPrice yearPrice;
      String id;

      id = converted.getString("Value");

      Interaction.getInstance().setYearPriceId(id);
      yearPrice = this.convert(reference, model);
      result.add(yearPrice);

      this.informIncrement();
    }

    // Tentativa de acelerar o processo, não necessitando que a conexão seja aberta várias vezes
    this.yearPricePT.create(result);

    return result;
  }

  private YearPrice convert(Reference reference, Model model) {
    JSONObject object = new JSONObject(Interaction.getInstance().getLastResponse().body());
    YearPrice result = new YearPrice();
    Float value;
    String fuel;
    int year;
    String fipe;
    String authentication;

    value = Float.parseFloat(object.getString("Valor").replace("R$", "").replace(".", "").replace(",", ".").trim());
    year = object.getInt("AnoModelo");
    fuel = object.getString("Combustivel").toUpperCase();
    fipe = object.getString("CodigoFipe");
    authentication = object.getString("Autenticacao");

    // Caso esteja 32000 o ano é de referência
    if (year == 32000) {
      year = reference.getYear();
      result.setZero(true);
    } else {
      result.setZero(false);
    }

    result.setYear(year);
    result.setPrice(value);
    result.setModel(model);
    result.setReference(reference);
    result.setFuelType(FuelType.getFuelType(fuel));
    result.setFipe(fipe);
    result.setAuthentication(authentication);

    return result;
  }

  public YearPrice searchLast() {
    return this.yearPricePT.searchLast();
  }
}
