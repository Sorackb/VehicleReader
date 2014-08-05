package org.lucassouza.vehiclereader.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lucassouza.tools.PropertyTool;

/**
 *
 * @author Lucas Souza [lucas.souza@virtual.inf.br]
 */
public class Configuration {

  private static PropertyTool iniFile;
  private static HashMap<String, String> bdConfig;

  public static void lerArquivo() {
    String systemPath;
    File system;

    if (iniFile == null) {
      iniFile = new PropertyTool();
      systemPath = Configuration.class.getProtectionDomain().getCodeSource()
              .getLocation().getPath();
      system = new File(systemPath);

      try {
        if (system.getParent().contains("target")) {
          iniFile.readPropertyFile("C:/VehicleReader/config.ini");
        } else {
          iniFile.readPropertyFile(system.getParent() + "/config.ini");
        }
      } catch (IOException ex) {
        Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  public static String getPUName() {
    return "VehicleReaderPU";
  }

  public static HashMap<String, String> getBDConfig() {
    String dataBaseLocation;

    if (bdConfig == null) {
      bdConfig = new HashMap<>();

      lerArquivo();
      dataBaseLocation = "jdbc:sqlserver://" + iniFile.getProperty("connection.host") + ";"
              + "databaseName=" + iniFile.getProperty("connection.database");
      bdConfig.put("javax.persistence.jdbc.url", dataBaseLocation);
      bdConfig.put("javax.persistence.jdbc.user", iniFile.getProperty("connection.user"));
      bdConfig.put("javax.persistence.jdbc.password", iniFile.getProperty("connection.password"));
    }

    return bdConfig;
  }
}
