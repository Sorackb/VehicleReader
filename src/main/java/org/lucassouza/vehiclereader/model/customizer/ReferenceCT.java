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
    StoredProcedureCall procedure = new StoredProcedureCall();

    procedure.setProcedureName("FIPE.INSERT_REFERENCE");
    procedure.addNamedInOutputArgument("PID", "ID");
    procedure.addNamedArgument("PDESCRIPTION", "DESCRIPTION_PT");
    procedure.addNamedArgument("PMONTH", "MONTH");
    procedure.addNamedArgument("PYEAR", "YEAR");

    descriptor.getQueryManager().setInsertCall(procedure);
  }
}
