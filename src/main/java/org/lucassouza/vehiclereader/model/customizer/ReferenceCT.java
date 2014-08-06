package org.lucassouza.vehiclereader.model.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ReferenceCT implements DescriptorCustomizer {

  @Override
  public void customize(ClassDescriptor descriptor) {
    StoredProcedureCall insertProc = new StoredProcedureCall();

    insertProc.setProcedureName("FIPE.SP_REFERENCE_INS");
    insertProc.addNamedInOutputArgument("PID", "ID");
    insertProc.addNamedArgument("PDESCRIPTION", "DESCRIPTION_PT");
    insertProc.addNamedArgument("PMONTH", "MONTH");
    insertProc.addNamedArgument("PYEAR", "YEAR");

    descriptor.getQueryManager().setInsertCall(insertProc);
  }
}
