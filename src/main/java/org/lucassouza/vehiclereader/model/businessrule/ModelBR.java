package org.lucassouza.vehiclereader.model.businessrule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.ModelPT;
import org.lucassouza.vehiclereader.pojo.Brand;
import org.lucassouza.vehiclereader.pojo.Model;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ModelBR extends BasicBR {

  private final ModelPT modelPT;
  private Model lastModel;

  public ModelBR() {
    this.modelPT = new ModelPT();
    this.resourceType = ResourceType.MODEL;
    this.proceed = true;
  }

  public List<Model> readAll(Interaction interaction, VehicleClassification classification,
          Reference reference, Brand brand) throws IOException {
    List<Model> result = new ArrayList<>();
    YearPriceBR yearPriceBR = new YearPriceBR();
    JSONObject models;

    models = new JSONObject(interaction.getLastResponse().body());
    this.informAmount(models.getJSONArray("Modelos").length());

    yearPriceBR.setLast(this.lastYearPrice);
    this.lastYearPrice = null;
    yearPriceBR.communicateInterest(this.observerList);

    for (Object object : models.getJSONArray("Modelos")) {
      Model model = this.convert(classification, brand, (JSONObject) object);

      if (!this.proceed && this.lastModel.equals(model)) {
        this.proceed = true;
      }

      if (this.proceed) {
        result.add(model);
        // Caso não precise ser lido, apenas incrementa pra indicar que já está completo
      } else {
        this.informIncrement();
      }
    }

    // Tentativa de acelerar o processo, não necessitando que a conexão seja aberta várias vezes
    modelPT.create(result);

    for (Model model : result) {
      interaction.setModelId(model.getId());
      // TODO Send interaction
      yearPriceBR.readAll(null, reference, model);
      this.informIncrement();
    }

    return result;
  }

  private Model convert(VehicleClassification classification, Brand brand,
          JSONObject model) {
    String description;
    Model result;
    int id;

    id = model.getInt("Value");
    description = model.getString("Label");
    result = new Model();

    result.setId(id);
    result.setDescription(description);
    result.setBrand(brand);
    result.setVehicleClassification(classification);

    return result;
  }

  @Override
  public void setLast(YearPrice lastYearPrice) {
    super.setLast(lastYearPrice);

    if (lastYearPrice != null) {
      this.lastModel = lastYearPrice.getModel();
    }
  }
}
