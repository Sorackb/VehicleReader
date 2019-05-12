package org.lucassouza.vehiclereader.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lucassouza.tools.PropertyTool;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Configuration {

  private static PropertyTool iniFile;
  // Global para não ser verificado a cada momento
  private static HashMap<String, String> bdConfig;

  public static void readFile() {
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

  public static Map<String, String> getBDConfig() {
    String dataBaseLocation;

    if (bdConfig == null) {
      bdConfig = new HashMap<>();

      readFile();

      // Supported DBMS: SQLServer and MariaDB
      switch (iniFile.getProperty("connection.dbms", "SQLServer")) {
        case "MariaDB":
          bdConfig.put("javax.persistence.jdbc.driver", "org.mariadb.jdbc.Driver");
          dataBaseLocation = "jdbc:mariadb://" + iniFile.getProperty("connection.host", "127.0.0.1") + "/";
          break;
        default:
          bdConfig.put("javax.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
          dataBaseLocation = "jdbc:sqlserver://" + iniFile.getProperty("connection.host", "127.0.0.1") + ";databaseName=";
      }

      dataBaseLocation = dataBaseLocation + iniFile.getProperty("connection.database", "");
      bdConfig.put("javax.persistence.jdbc.url", dataBaseLocation);
      bdConfig.put("javax.persistence.jdbc.user", iniFile.getProperty("connection.user", ""));
      bdConfig.put("javax.persistence.jdbc.password", iniFile.getProperty("connection.password", ""));
    }

    return bdConfig;
  }
}
