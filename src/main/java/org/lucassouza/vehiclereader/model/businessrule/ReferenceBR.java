package org.lucassouza.vehiclereader.model.businessrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.ReferencePT;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ReferenceSituation;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferenceBR extends BasicBR {

  private Reference lastReference;
  private VehicleClassification lastClassification;
  private Boolean proceedClassification;
  private Boolean proceedReference;
  private List<Reference> readingList;

  public ReferenceBR() {
    this.resourceType = ResourceType.REFERENCE;
    this.proceedReference = true;
    this.proceedClassification = true;
  }

  public ReferenceBR(List<Reference> readingList) {
    this();
    this.readingList = readingList;
  }

  public List<Reference> updateReferences() {
    Interaction interaction = new Interaction();
    ReferencePT referencePT = new ReferencePT();
    List<Reference> result;
    Elements referenceList;

    // Utiliza um tipo padrão para ler as referências
    interaction.setClassification(VehicleClassification.CAR);
    referenceList = interaction.getPageSource().select(
            "select#ddlTabelaReferencia > option");

    for (Element referenceElement : referenceList) {
      Reference reference = this.convert(referenceElement);
      // Caso nenhum seja passado executa todas as referências
      referencePT.create(reference);
    }

    result = referencePT.readAll("id", false);

    return result;
  }

  public void readAll() {
    ReferencePT referencePT = new ReferencePT();

    if (this.readingList == null) {
      this.readingList = this.updateReferences();
    }

    this.informAmount(this.readingList.size());

    /* Separei a continuidade para implementar o sistema de flag de finalização,
     * impedindo que leituras que já foram executadas ocupem processamento.
     */
    for (Reference reference : this.readingList) {
      // Caso já esteja completo apenas segue em frente
      if (ReferenceSituation.COMPLETE.equals(reference.getReferenceSituation())) {
        this.informIncrement();
        continue;
      }

      if (!this.proceedReference && this.lastReference.equals(reference)) {
        this.proceedReference = true;
      }

      // Caso não deva continuar, vai para o próximo registro
      if (!this.proceedReference) {
        this.informIncrement();
        continue;
      }

      reference.setReferenceSituation(ReferenceSituation.INCOMPLETE);
      referencePT.update(reference);

      // Desconsidera o tipo de veículo "NENHUM"
      this.informAmount(ResourceType.VEHICLE_CLASSIFICATION, VehicleClassification.values().length - 1);

      for (VehicleClassification classification : VehicleClassification.values()) {
        // Desconsidera o tipo de veículo "NENHUM"
        if (classification.getId().equals(0)) {
          continue;
        }

        if (!this.proceedClassification && this.lastClassification.equals(classification)) {
          this.proceedClassification = true;
        }

        if (this.proceedClassification) {
          Interaction interaction = new Interaction();

          interaction.setClassification(classification);
          this.continueReading(interaction, reference, classification);
        }

        this.informIncrement(ResourceType.VEHICLE_CLASSIFICATION);
      }

      reference.setReferenceSituation(ReferenceSituation.COMPLETE);
      referencePT.update(reference);

      this.informIncrement();
    }
  }

  private void continueReading(Interaction interaction, Reference reference,
          VehicleClassification classification) {
    BrandBR brandBR = new BrandBR();

    if (interaction.getElementAttr("select#ddlTabelaReferencia > option[value="
            + reference.getId() + "]", "selected").isEmpty()) {
      interaction.setReferenceId(reference.getId());
    }

    brandBR.setLast(this.lastYearPrice);
    this.lastYearPrice = null;
    brandBR.communicateInterest(this.observerList);
    brandBR.readAll(interaction, classification, reference);
  }

  private Reference convert(Element reference) {
    SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
    SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
    SimpleDateFormat formatterMonthExt = new SimpleDateFormat("MMM");
    SimpleDateFormat formatterExt = new SimpleDateFormat("MMM/yyyy");
    SimpleDateFormat formatterExtInv = new SimpleDateFormat("yyyy/MMM");
    Calendar calendar = Calendar.getInstance();
    String description = reference.text().replace(" ", "");
    Reference result = new Reference();
    String[] textPart;
    Date today;
    Integer month;
    Integer year;

    result.setId(Integer.parseInt(reference.attr("value")));

    if (description.toUpperCase().equals("ATUAL")) {
      month = Integer.parseInt(formatterMonth.format(new Date()));
      year = Integer.parseInt(formatterYear.format(new Date()));
      today = new Date();
      result.setDescription(formatterExt.format(today));
      result.setMonth(month);
      result.setYear(year);
    } else {
      textPart = description.split("/");

      try {
        today = formatterExtInv.parse(description);
        result.setDescription(formatterExt.format(today));
        calendar.setTime(formatterMonthExt.parse(textPart[1]));
        result.setMonth(calendar.get(Calendar.MONTH) + 1);
      } catch (ParseException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
      }
      result.setYear(Integer.parseInt(textPart[0].trim()));
    }

    return result;
  }

  private void informAmount(ResourceType resourceType, Integer amount) {
    // Slightly modified in relation to inherited method
    if (this.observerList != null) {
      for (Communicable observer : this.observerList) {
        observer.informAmount(resourceType, amount);
      }
    }
  }

  private void informIncrement(ResourceType resourceType) {
    // Slightly modified in relation to inherited method
    if (this.observerList != null) {
      for (Communicable observer : this.observerList) {
        observer.informIncrement(resourceType);
      }
    }
  }

  @Override
  public void setLast(YearPrice lastYearPrice) {
    super.setLast(lastYearPrice);

    if (lastYearPrice != null) {
      this.proceedClassification = false;
      this.lastReference = lastYearPrice.getReference();
      this.lastClassification = lastYearPrice.getModel().getVehicleClassification();
    }
  }
}
