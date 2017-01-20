package org.lucassouza.vehiclereader.model.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class ModelCT implements DescriptorCustomizer {

  @Override
  public void customize(ClassDescriptor descriptor) {
    StoredProcedureCall procedure = new StoredProcedureCall();

    procedure.setProcedureName("FIPE.INSERT_MODEL");
    procedure.addNamedArgument("PID", "ID");
    procedure.addNamedArgument("PID_BRAND", "ID_BRAND");
    procedure.addNamedArgument("PID_VEHICLE_CLASSIFICATION", "ID_VEHICLE_CLASSIFICATION");
    procedure.addNamedArgument("PDESCRIPTION", "DESCRIPTION");

    descriptor.getQueryManager().setInsertCall(procedure);
  }
}
