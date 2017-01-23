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
    StoredProcedureCall procedure = new StoredProcedureCall();

    procedure.setProcedureName("FIPE.INSERT_YEAR_PRICE");
    procedure.addNamedInOutputArgument("PID", "ID");
    procedure.addNamedArgument("PID_MODEL", "ID_MODEL");
    procedure.addNamedArgument("PID_REFERENCE", "ID_REFERENCE");
    procedure.addNamedArgument("PID_FUEL_TYPE", "ID_FUEL_TYPE");
    procedure.addNamedArgument("PYEAR", "YEAR");
    procedure.addNamedArgument("PPRICE", "PRICE");
    procedure.addNamedArgument("PFIPE", "FIPE");
    procedure.addNamedArgument("PAUTHENTICATION", "AUTHENTICATION");
    procedure.addNamedArgument("PZERO", "ZERO");

    descriptor.getQueryManager().setInsertCall(procedure);
  }
}
