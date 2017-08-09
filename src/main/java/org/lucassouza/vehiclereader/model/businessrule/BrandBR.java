package org.lucassouza.vehiclereader.model.businessrule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.BrandPT;
import org.lucassouza.vehiclereader.pojo.Brand;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BrandBR extends BasicBR {

  private Brand lastBrand;

  public BrandBR() {
    this.resourceType = ResourceType.BRAND;
    this.proceed = true;
  }

  public List<Brand> readAll(VehicleClassification classification, Reference reference) throws IOException, InterruptedException, ExecutionException {
    List<Brand> result = new ArrayList<>();
    BrandPT brandPT = new BrandPT();
    ModelBR modelBR = new ModelBR();
    JSONArray list;

    list = new JSONArray(Interaction.getInstance().getLastResponse().body());
    this.informAmount(list.length());

    modelBR.setLast(this.lastYearPrice);
    this.lastYearPrice = null;
    modelBR.communicateInterest(this.observerList);

    for (Object object : list) {
      Brand brand = this.convert((JSONObject) object);

      if (!this.proceed && this.lastBrand.equals(brand)) {
        this.proceed = true;
      }

      if (this.proceed) {
        result.add(brand);
      } else {
        this.informIncrement();
      }
    }

    brandPT.create(result);

    for (Brand brand : result) {
      Interaction.getInstance().setBrandId(brand.getId());
      modelBR.readAll(classification, reference, brand);
      this.informIncrement();
    }

    return result;
  }

  private Brand convert(JSONObject brand) {
    Integer id = Integer.parseInt(brand.getString("Value"));
    String description;
    Brand result;

    description = brand.getString("Label");
    result = new Brand();

    result.setId(id);
    result.setDescription(description);

    return result;
  }

  @Override
  public void setLast(YearPrice lastYearPrice) {
    super.setLast(lastYearPrice);

    if (lastYearPrice != null) {
      this.lastBrand = lastYearPrice.getModel().getBrand();
    }
  }
}
