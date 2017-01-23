package org.lucassouza.vehiclereader.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.lucassouza.vehiclereader.controller.Communicable;
import org.lucassouza.vehiclereader.controller.Reader;
import org.lucassouza.vehiclereader.type.ResourceType;

/**
 *
 * @author Lucas Bernardo [sorackb@gmail.com]
 */
public class FrmReader extends JFrame implements Communicable {

  private static final long serialVersionUID = 1;
  private final List<Integer> speedList = new ArrayList<>();
  private Long tick;
  private Integer yearPriceByMinute;

  /**
   * Creates new form FrmLeitor
   */
  public FrmReader() {
    initComponents();
    this.pgbReference.setStringPainted(true);
    this.pgbVehicleClassification.setStringPainted(true);
    this.pgbBrand.setStringPainted(true);
    this.pgbModel.setStringPainted(true);
    this.pgbYearPrice.setStringPainted(true);
    this.redrawnChart();
  }

  private void redrawnChart() {
    ChartPanel chartPanel;
    JFreeChart chart;
    XYSeriesCollection dataset;
    XYSeries xySeries = new XYSeries("Velocidade");

    for (int i = 0; i < this.speedList.size(); i++) {
      Long index = this.tick / 6L;

      if (index > 100L) {
        index = index - this.speedList.size() + i;
      } else {
        index = new Long(i + 1);
      }

      xySeries.add(index, this.speedList.get(i));
    }

    this.pnlChart.removeAll();
    this.pnlChart.revalidate();
    dataset = new XYSeriesCollection();
    dataset.addSeries(xySeries);
    chart = ChartFactory.createXYLineChart("Velocidade de leitura", "Minutos de execução",
            "Quantidade Ano/Modelo", dataset, PlotOrientation.VERTICAL, true,
            true, false);
    chart.removeLegend();
    chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(600, 300));
    this.pnlChart.setLayout(new BorderLayout());
    this.pnlChart.add(chartPanel, BorderLayout.CENTER);
  }

  private static void startSystemTray() {
    final FrmReader frame = new FrmReader();

    Image imgLogo = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(
            "resource/logo.png"));
    Image imgWindow = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(
            "resource/window.png"));
    frame.setIconImage(imgWindow);
    //Checa se há suporte a system tray
    if (!SystemTray.isSupported()) {
      System.out.println("SystemTray is not supported");
      return;
    }
    //Cria o popupmenu
    final PopupMenu popup = new PopupMenu();

    //Cria o icone da tray
    final TrayIcon trayIcon
            = new TrayIcon(imgLogo, frame.getTitle(), popup);
    final SystemTray tray = SystemTray.getSystemTray();

    // cria os itens do menu
    MenuItem aboutItem = new MenuItem("Abrir");
    MenuItem exitItem = new MenuItem("Sair");

    //Coloca os itens no menu
    popup.add(aboutItem);
    popup.addSeparator();
    popup.add(exitItem);

    //Adiciona o popup no tray
    trayIcon.setPopupMenu(popup);

    try {
      tray.add(trayIcon);
    } catch (AWTException e) {
      System.out.println("TrayIcon could not be added.");
    }

    //Cria o listener para abrir o jframe quando clicar
    trayIcon.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        if (frame.isVisible()) {
          frame.setVisible(false);
        } else {
          frame.setVisible(true);
        }
      }
    });
    //Cria o listener para esconder o jframe quando mandar fechar
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent evt) {
        String[] str = {"Sair", "Minimizar"};
        int result = JOptionPane.showOptionDialog(frame,
                "Você quer sair ou minimizar?", "Sair ou Minimizar?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, str[1]);
        if (result == 0) {
          frame.dispose();
          System.exit(0);
        } else {
          frame.setVisible(false);
        }//end else
      }//end windowClosing
    });//end WindowAdapter
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    pnlGeral = new javax.swing.JPanel();
    pgbReference = new javax.swing.JProgressBar();
    btnIniciar = new javax.swing.JToggleButton();
    pgbModel = new javax.swing.JProgressBar();
    pgbYearPrice = new javax.swing.JProgressBar();
    lblAnoValor = new javax.swing.JLabel();
    lblModelo = new javax.swing.JLabel();
    lblMarca = new javax.swing.JLabel();
    lblReferencia = new javax.swing.JLabel();
    lblTipoVeiculo = new javax.swing.JLabel();
    pgbVehicleClassification = new javax.swing.JProgressBar();
    lblMedia = new javax.swing.JLabel();
    pnlChart = new javax.swing.JPanel();
    pgbBrand = new javax.swing.JProgressBar();

    pnlGeral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    pnlGeral.add(pgbReference, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 100, -1));

    btnIniciar.setText("Iniciar");
    btnIniciar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnIniciarActionPerformed(evt);
      }
    });
    pnlGeral.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));
    pnlGeral.add(pgbModel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 100, -1));
    pnlGeral.add(pgbYearPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 100, -1));

    lblAnoValor.setText("Ano/Valores:");
    pnlGeral.add(lblAnoValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

    lblModelo.setText("Modelos:");
    pnlGeral.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

    lblMarca.setText("Marcas:");
    pnlGeral.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

    lblReferencia.setText("Referências:");
    pnlGeral.add(lblReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

    lblTipoVeiculo.setText("Veículos:");
    pnlGeral.add(lblTipoVeiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
    pnlGeral.add(pgbVehicleClassification, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 100, -1));
    pnlGeral.add(lblMedia, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 63, 14));

    javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
    pnlChart.setLayout(pnlChartLayout);
    pnlChartLayout.setHorizontalGroup(
      pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );
    pnlChartLayout.setVerticalGroup(
      pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 320, Short.MAX_VALUE)
    );

    pnlGeral.add(pnlChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 124, 560, 320));
    pnlGeral.add(pgbBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 100, -1));

    getContentPane().add(pnlGeral, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
    Reader leitor = new Reader(this);

    this.yearPriceByMinute = 0;
    this.tick = 0L;
    new javax.swing.Timer(10000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Integer mediaMin = yearPriceByMinute * 6;

        lblMedia.setText(mediaMin.toString() + "/min.");
        yearPriceByMinute = 0;
        tick++;

        if ((tick % 6L) == 0L) {
          if (speedList.size() >= 100) {
            speedList.remove(0);
          }
          speedList.add(mediaMin);

          if ((tick / 6L) >= 2) {
            redrawnChart();
          }
        }
      }
    }).start();
    leitor.start();
    this.btnIniciar.setEnabled(false);
  }//GEN-LAST:event_btnIniciarActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(FrmReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(FrmReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(FrmReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(FrmReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        FrmReader.startSystemTray();
        //new FrmReader().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JToggleButton btnIniciar;
  private javax.swing.JLabel lblAnoValor;
  private javax.swing.JLabel lblMarca;
  private javax.swing.JLabel lblMedia;
  private javax.swing.JLabel lblModelo;
  private javax.swing.JLabel lblReferencia;
  private javax.swing.JLabel lblTipoVeiculo;
  private javax.swing.JProgressBar pgbBrand;
  private javax.swing.JProgressBar pgbModel;
  private javax.swing.JProgressBar pgbReference;
  private javax.swing.JProgressBar pgbVehicleClassification;
  private javax.swing.JProgressBar pgbYearPrice;
  private javax.swing.JPanel pnlChart;
  private javax.swing.JPanel pnlGeral;
  // End of variables declaration//GEN-END:variables

  @Override
  public void informAmount(ResourceType resourceType, Integer amount) {
    JProgressBar pgbRefresh = null;

    switch (resourceType) {
      case REFERENCE:
        pgbRefresh = this.pgbReference;
        break;
      case VEHICLE_CLASSIFICATION:
        pgbRefresh = this.pgbVehicleClassification;
        break;
      case BRAND:
        pgbRefresh = this.pgbBrand;
        break;
      case MODEL:
        pgbRefresh = this.pgbModel;
        break;
      case YEAR_PRICE:
        pgbRefresh = this.pgbYearPrice;
        break;
    }

    if (pgbRefresh != null) {
      pgbRefresh.setValue(0);
      pgbRefresh.setMinimum(0);
      pgbRefresh.setMaximum(amount);
    }
  }

  @Override
  public void informIncrement(ResourceType resourceType) {
    JProgressBar pgbRefresh = null;

    switch (resourceType) {
      case REFERENCE:
        pgbRefresh = this.pgbReference;
        break;
      case VEHICLE_CLASSIFICATION:
        pgbRefresh = this.pgbVehicleClassification;
        break;
      case BRAND:
        pgbRefresh = this.pgbBrand;
        break;
      case MODEL:
        pgbRefresh = this.pgbModel;
        break;
      case YEAR_PRICE:
        this.yearPriceByMinute++;
        pgbRefresh = this.pgbYearPrice;
        break;
    }

    if (pgbRefresh != null) {
      pgbRefresh.setValue(pgbRefresh.getValue() + 1);
    }
  }
}
