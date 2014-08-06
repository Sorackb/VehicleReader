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
    StoredProcedureCall insertProc = new StoredProcedureCall();

    insertProc.setProcedureName("FIPE.SP_MODEL_INS");
    insertProc.addNamedArgument("PID", "ID");
    insertProc.addNamedArgument("PID_BRAND", "ID_BRAND");
    insertProc.addNamedArgument("PID_VEHICLE_CLASSIFICATION", "ID_VEHICLE_CLASSIFICATION");
    insertProc.addNamedArgument("PDESCRIPTION", "DESCRIPTION");

    descriptor.getQueryManager().setInsertCall(insertProc);
  }
}
