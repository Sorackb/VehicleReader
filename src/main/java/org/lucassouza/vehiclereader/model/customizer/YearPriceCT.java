package org.lucassouza.vehiclereader.model.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class YearPriceCT implements DescriptorCustomizer {

  @Override
  public void customize(ClassDescriptor descriptor) {
    StoredProcedureCall insertProc = new StoredProcedureCall();

    insertProc.setProcedureName("FIPE.SP_YEAR_PRICE_INS");
    insertProc.addNamedInOutputArgument("PID", "ID");
    insertProc.addNamedArgument("PID_MODEL", "ID_MODEL");
    insertProc.addNamedArgument("PID_REFERENCE", "ID_REFERENCE");
    insertProc.addNamedArgument("PID_FUEL_TYPE", "ID_FUEL_TYPE");
    insertProc.addNamedArgument("PYEAR", "YEAR");
    insertProc.addNamedArgument("PPRICE", "PRICE");

    descriptor.getQueryManager().setInsertCall(insertProc);
  }
}
