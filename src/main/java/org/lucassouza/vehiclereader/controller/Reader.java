package org.lucassouza.vehiclereader.controller;

import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.businessrule.BrandBR;
import org.lucassouza.vehiclereader.model.businessrule.ModelBR;
import org.lucassouza.vehiclereader.model.businessrule.ReferenceBR;
import org.lucassouza.vehiclereader.model.businessrule.YearPriceBR;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ReferenceSituation;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Reader extends Thread implements Communicable {

  private final Communicable observer;

  public Reader(Communicable observer) {
    this.observer = observer;
  }

  private void evaluate() {
    Interaction interaction;
    YearPriceBR yearPriceBR;
    ModelBR modelBR;
    BrandBR brandBR;
    ReferenceBR referenceBR;
    YearPrice lastYearPrice;

    // Configuração de proxy
    /*System.setProperty("http.proxyHost", "192.168.111.251");
     System.setProperty("http.proxyPort", "3128");
     System.setProperty("http.proxyUser", "lucas.souza");
     System.setProperty("http.proxyPassword", "swordfish");*/
    interaction = new Interaction();
    yearPriceBR = new YearPriceBR(interaction, this.observer);
    modelBR = new ModelBR(yearPriceBR, interaction, this.observer);
    brandBR = new BrandBR(modelBR, interaction, this.observer);
    referenceBR = new ReferenceBR(brandBR, interaction, this.observer);

    lastYearPrice = yearPriceBR.searchLast();

    /* Verifica último para passar os pontos de início para as respectivas
     * Regras de Negócio
     */
    if (lastYearPrice != null && lastYearPrice.getReference().getReferenceSituation().equals(ReferenceSituation.INCOMPLETE)) {
      referenceBR.setLastReference(lastYearPrice.getReference());
      referenceBR.setLastClassification(lastYearPrice.getModel().getVehicleClassification());
      brandBR.setLastBrand(lastYearPrice.getModel().getBrand());
      modelBR.setLastModel(lastYearPrice.getModel());
      yearPriceBR.setLastYearPrice(lastYearPrice);
    }

    referenceBR.readAll();
  }

  @Override
  public void run() {
    this.evaluate();
  }

  @Override
  public void informAmount(ResourceType resourceType, Integer amount) {
    if (this.observer != null) {
      this.observer.informAmount(resourceType, amount);
    }
  }

  @Override
  public void informIncrement(ResourceType resourceType) {
    if (this.observer != null) {
      this.observer.informIncrement(resourceType);
    }
  }
}
