package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

  public List<Brand> readAll(Interaction interaction, VehicleClassification classification,
          Reference reference) {
    Elements brandList = interaction.getPageSource().select(
            "select#ddlMarca > option:not(:nth-of-type(1))");
    List<Brand> result = new ArrayList<>();
    BrandPT brandPT = new BrandPT();
    ModelBR modelBR = new ModelBR();

    this.informAmount(brandList.size());
    
    modelBR.setLast(this.lastYearPrice);
    this.lastYearPrice = null;
    modelBR.communicateInterest(this.observerList);

    for (Element brandElement : brandList) {
      Brand brand = this.convert(brandElement);

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
      interaction.setBrandId(brand.getId());
      modelBR.readAll(interaction, classification, reference, brand);
      this.informIncrement();
    }

    return result;
  }

  private Brand convert(Element brand) {
    Integer id = Integer.parseInt(brand.attr("value"));
    String description;
    Brand result;

    description = brand.text();
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
