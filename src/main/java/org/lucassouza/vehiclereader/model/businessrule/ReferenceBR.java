package org.lucassouza.vehiclereader.model.businessrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.model.Interaction;
import org.lucassouza.vehiclereader.model.persistence.ReferencePT;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.type.ReferenceSituation;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferenceBR extends BasicBR {

  private final ReferencePT referencePT;
  private final BrandBR brandRN;
  private Reference lastReference;
  private VehicleClassification lastClassification;
  private Boolean proceedClassification;
  private Boolean proceedReference;

  public ReferenceBR(BrandBR brandBR, Interaction interaction) {
    this.resourceType = ResourceType.REFERENCE;
    this.referencePT = new ReferencePT();
    this.interaction = interaction;
    this.brandRN = brandBR;
    this.proceedReference = true;
    this.proceedClassification = true;
  }

  public LinkedHashSet<Reference> readAll() {
    LinkedHashSet<Reference> result;
    LinkedHashSet<Reference> completedList;
    Elements referenceList;

    // Utiliza um tipo padrão para ler as referências
    this.interaction.setClassification(VehicleClassification.CAR);
    referenceList = this.interaction.getPageSource().select(
            "select#ddlTabelaReferencia > option");

    for (Element referenceElement : referenceList) {
      Reference reference = this.convert(referenceElement);
      // Caso nenhum seja passado executa todas as referências
      this.referencePT.create(reference);
    }

    completedList = this.referencePT.readCompleted();
    result = this.referencePT.readPending();

    this.informAmount(result.size() + completedList.size());

    for (int i = 0; i < completedList.size(); i++) {
      this.informIncrement();
    }

    /* Separei a continuidade para implementar o sistema de flag de finalização,
     * impedindo que leituras que já foram executadas ocupem processamento.
     */
    for (Reference reference : result) {
      if (!this.proceedReference && this.lastReference.equals(reference)) {
        this.proceedReference = true;
      }
      if (this.proceedReference) {

        reference.setReferenceSituation(ReferenceSituation.INCOMPLETE);
        this.referencePT.update(reference);

        if (this.observerList != null) {
          for (Communicable observer : this.observerList) {
            observer.informAmount(ResourceType.VEHICLE_CLASSIFICATION, ResourceType.values().length - 1);
          }
        }

        for (VehicleClassification classification : VehicleClassification.values()) {
          // Desconsidera o tipo de veículo "NENHUM"
          if (!classification.getId().equals(0)) {
            if (!this.proceedClassification && this.lastClassification.equals(classification)) {
              this.proceedClassification = true;
            }

            if (this.proceedClassification) {
              this.interaction.setClassification(classification);
              this.continueReading(reference, classification);
            }

            if (this.observerList != null) {
              for (Communicable observer : this.observerList) {
                observer.informIncrement(ResourceType.VEHICLE_CLASSIFICATION);
              }
            }
          }
        }

        reference.setReferenceSituation(ReferenceSituation.COMPLETE);
        this.referencePT.update(reference);
      }

      this.informIncrement();
    }

    return result;
  }

  private void continueReading(Reference reference, VehicleClassification classification) {
    if (this.interaction.getElementAttr("select#ddlTabelaReferencia > option[value="
            + reference.getId() + "]", "selected").equals("")) {
      this.interaction.setReferenceId(reference.getId());
    }
    this.brandRN.readAll(classification, reference);
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

  public void setLastReference(Reference lastReference) {
    if (lastReference != null) {
      this.proceedReference = false;
    }
    this.lastReference = lastReference;
  }

  public void setLastClassification(VehicleClassification lastClassification) {
    if (lastClassification != null) {
      this.proceedClassification = false;
    }
    this.lastClassification = lastClassification;
  }
}
