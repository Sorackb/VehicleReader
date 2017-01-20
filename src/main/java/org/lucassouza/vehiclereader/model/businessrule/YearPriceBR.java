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
    this.proceed = true;
  }

  public List<YearPrice> readAll(Interaction interaction, Reference reference,
          Model model) throws IOException {
    List<YearPrice> result = new ArrayList<>();
    JSONArray list;
    String id;

    list = new JSONArray(interaction.getLastResponse().body());
    this.informAmount(list.length());

    for (Object object : list) {
      JSONObject converted = (JSONObject) object;
      YearPrice yearPrice;
      id = converted.getString("Value");

      if (this.proceed) {
        interaction.setYearPriceId(id);
        yearPrice = this.convert(interaction, reference, model);
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

  private YearPrice convert(Interaction interaction, Reference reference,
          Model model) {
    JSONObject object = new JSONObject(interaction.getLastResponse().body());
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

    // Caso esteja 3200 o ano é de referência
    if (year == 3200) {
      year = reference.getYear();
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
