package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
          Reference reference,
          Brand brand) {
    Elements modelList = interaction.getPageSource().select(
            "select#ddlModelo > option:not(:nth-of-type(1))");
    List<Model> result = new ArrayList<>();
    YearPriceBR yearPriceBR = new YearPriceBR();

    this.informAmount(modelList.size());
    
    yearPriceBR.setLast(this.lastYearPrice);
    this.lastYearPrice = null;
    yearPriceBR.communicateInterest(this.observerList);

    for (Element modelElement : modelList) {
      Model model = this.convert(classification, brand, modelElement);

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
      yearPriceBR.readAll(interaction, reference, model);
      this.informIncrement();
    }

    return result;
  }

  private Model convert(VehicleClassification classification, Brand brand,
          Element model) {
    String fipe = model.attr("value");
    String description;
    Model result;

    description = model.text();
    result = new Model();

    result.setId(fipe);
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
