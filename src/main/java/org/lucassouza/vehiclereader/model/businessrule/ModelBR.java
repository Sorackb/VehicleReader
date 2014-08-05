package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.ModelPT;
import org.lucassouza.vehiclereader.pojo.Brand;
import org.lucassouza.vehiclereader.pojo.Model;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [lucas.souza@virtual.inf.br]
 */
public class ModelBR extends BasicBR {

  private final Interaction interaction;
  private final ModelPT modelPT;
  private final YearPriceBR yearPriceBR;
  private Model lastModel;
  private Boolean proceed;

  public ModelBR(YearPriceBR yearPriceBR, Interaction interaction, Communicable observer) {
    this.observer = observer;
    this.resourceType = ResourceType.MODEL;
    this.modelPT = new ModelPT();
    this.interaction = interaction;
    this.yearPriceBR = yearPriceBR;
    this.proceed = true;
  }

  public List<Model> readAll(VehicleClassification classification, Reference reference,
          Brand brand) {
    Elements modelList = this.interaction.getPageSource().select(
            "select#ddlModelo > option:not(:nth-of-type(1))");
    List<Model> result = new ArrayList<>();

    this.informAmount(modelList.size());

    for (Element modelElement : modelList) {
      Model model = this.convert(classification, brand, modelElement);

      if (!this.proceed && this.lastModel.equals(model)) {
        this.proceed = true;
      }

      if (this.proceed) {
        result.add(model);
      } else {
        // Caso não precise ser lido, apenas incrementa pra indicar que já está completo
        this.informIncrement();
      }
    }

    // Tentativa de acelerar o processo, não necessitando que a conexão seja aberta várias vezes
    this.modelPT.create(result);

    for (Model model : result) {
      this.interaction.setModelId(model.getId());
      this.yearPriceBR.readAll(reference, model);
      this.informIncrement();
    }

    return result;
  }

  private Model convert(VehicleClassification classification, Brand brand, Element model) {
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

  public void setLastModel(Model lastModel) {
    if (lastModel != null) {
      this.proceed = false;
    }
    this.lastModel = lastModel;
  }
}
