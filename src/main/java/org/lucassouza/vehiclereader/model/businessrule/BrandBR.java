package org.lucassouza.vehiclereader.model.businessrule;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.BrandPT;
import org.lucassouza.vehiclereader.pojo.Brand;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BrandBR extends BasicBR {

  private final BrandPT brandPT;
  private final ModelBR modelBR;
  private Brand lastBrand;
  private Boolean proceed;

  public BrandBR(ModelBR modelBR, Interaction interaction, Communicable observer) {
    this.observer = observer;
    this.resourceType = ResourceType.BRAND;
    this.brandPT = new BrandPT();
    this.interaction = interaction;
    this.modelBR = modelBR;
    this.proceed = true;
  }

  public List<Brand> readAll(VehicleClassification classification, Reference reference) {
    Elements brandList = this.interaction.getPageSource().select(
            "select#ddlMarca > option:not(:nth-of-type(1))");
    List<Brand> result = new ArrayList<>();

    this.informAmount(brandList.size());

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

    this.brandPT.create(result);

    for (Brand brand : result) {
      this.interaction.setBrandId(brand.getId());
      this.modelBR.readAll(classification, reference, brand);
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

  public void setLastBrand(Brand lastBrand) {
    if (lastBrand != null) {
      this.proceed = false;
    }

    this.lastBrand = lastBrand;
  }
}
