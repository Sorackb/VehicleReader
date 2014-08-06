package org.lucassouza.vehiclereader.model.persistence;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import org.lucassouza.dao.EclipseLinkPT;
import org.lucassouza.vehiclereader.pojo.Reference;
import org.lucassouza.vehiclereader.type.ReferenceSituation;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferencePT extends EclipseLinkPT<Reference> {

  public ReferencePT() {
    super(Configuration.getPUName(), Configuration.getBDConfig());
    this.objectClass = Reference.class;
  }

  public LinkedHashSet<Reference> readCompleted() {
    LinkedHashSet<Reference> result = new LinkedHashSet<>();
    LinkedHashMap<String, Object> condition = new LinkedHashMap<>();
    LinkedHashMap<String, String> orderBy = new LinkedHashMap<>();

    // Lê os COMPLETOS
    condition.put("referenceSituation", ReferenceSituation.COMPLETE);
    orderBy.put("year", "desc");
    orderBy.put("month", "desc");
    result.addAll(this.readList(condition, orderBy));

    return result;
  }

  public LinkedHashSet<Reference> readPending() {
    LinkedHashSet<Reference> result = new LinkedHashSet<>();
    LinkedHashMap<String, Object> incompletedCondition = new LinkedHashMap<>();
    LinkedHashMap<String, Object> pendingCondition = new LinkedHashMap<>();
    LinkedHashMap<String, String> incompletedOrderBy = new LinkedHashMap<>();
    LinkedHashMap<String, String> pendingOrderBy = new LinkedHashMap<>();

    // Lê os INCOMPLETOS
    incompletedCondition.put("referenceSituation", ReferenceSituation.INCOMPLETE);
    incompletedOrderBy.put("year", "desc");
    incompletedOrderBy.put("month", "desc");
    result.addAll(this.readList(incompletedCondition, incompletedOrderBy));

    // Lê os PENDENTES
    pendingCondition.put("referenceSituation", ReferenceSituation.PENDING);
    pendingOrderBy.put("year", "desc");
    pendingOrderBy.put("month", "desc");
    result.addAll(this.readList(pendingCondition, pendingOrderBy));

    return result;
  }
}
