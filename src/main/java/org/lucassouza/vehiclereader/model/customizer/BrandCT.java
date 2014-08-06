package org.lucassouza.vehiclereader.model.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.queries.StoredProcedureCall;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class BrandCT implements DescriptorCustomizer {

  @Override
  public void customize(ClassDescriptor descriptor) {
    StoredProcedureCall insertProc = new StoredProcedureCall();

    insertProc.setProcedureName("FIPE.SP_BRAND_INS");
    insertProc.addNamedInOutputArgument("PID", "ID");
    insertProc.addNamedArgument("PDESCRIPTION", "DESCRIPTION");

    descriptor.getQueryManager().setInsertCall(insertProc);
  }
}