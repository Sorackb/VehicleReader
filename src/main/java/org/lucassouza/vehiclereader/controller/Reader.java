package org.lucassouza.vehiclereader.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lucassouza.vehiclereader.model.businessrule.ReferenceBR;
import org.lucassouza.vehiclereader.model.businessrule.YearPriceBR;
import org.lucassouza.vehiclereader.pojo.YearPrice;
import org.lucassouza.vehiclereader.type.ResourceType;
import org.lucassouza.vehiclereader.utils.Configuration;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Reader extends Thread implements Communicable {

  private final Communicable observer;

  public Reader(Communicable observer) {
    this.observer = observer;
  }

  private void evaluate() {
    YearPriceBR yearPriceBR;
    ReferenceBR referenceBR;
    YearPrice lastYearPrice;

    yearPriceBR = new YearPriceBR();
    referenceBR = new ReferenceBR();

    referenceBR.communicateInterest(this.observer);
    lastYearPrice = yearPriceBR.searchLast();
    referenceBR.setLast(lastYearPrice);
    referenceBR.readAll();
  }

  @Override
  public void run() {
    String systemPath;
    File system;
    FileWriter fileWriter;
    PrintWriter printWriter;

    try {
      this.evaluate();
      // Grava o erro em um arquivo de log (error_log.txt)
    } catch (Exception ex) {
      Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
      try {
        systemPath = Configuration.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        system = new File(systemPath);

        if (system.getParent().contains("target")) {
          fileWriter = new FileWriter("C:/VehicleReader/error_log.txt", true);
        } else {
          fileWriter = new FileWriter(system.getParent() + "/error_log.txt", true);
        }
        printWriter = new PrintWriter(fileWriter);
        ex.printStackTrace(printWriter);
        printWriter.close();
        fileWriter.close();
      } catch (IOException ex1) {
        //Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex1);
      }
    }
  }

  @Override
  public void informAmount(ResourceType resourceType, Integer amount) {
    if (this.observer != null) {
      this.observer.informAmount(resourceType, amount);
    }
  }

  @Override
  public void informIncrement(ResourceType resourceType) {
    if (this.observer != null) {
      this.observer.informIncrement(resourceType);
    }
  }
}
