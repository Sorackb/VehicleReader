package org.lucassouza.vehiclereader.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class IniFile {
  private Properties properties;

  public void readFile(String path) throws IOException {
    this.properties = new Properties();

    String conteudoArquivo = fileToString(path);
    this.properties.load(new StringReader(conteudoArquivo.replace("\\", "\\\\")));
  }

  public String getProperty(String key) {
    return this.properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    return this.properties.getProperty(key, defaultValue);
  }

  private String fileToString(String path) {
    String result = null;
    DataInputStream in = null;

    try {
      File arquivo = new File(path);

      if (!arquivo.isFile()) {
        arquivo.createNewFile();
      }

      byte[] buffer = new byte[(int) arquivo.length()];
      in = new DataInputStream(new FileInputStream(arquivo));
      in.readFully(buffer);
      result = new String(buffer);
    } catch (IOException ex) {
      Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        in.close();
      } catch (IOException e) { //ignorar
      }
    }

    return result;
  }
}
