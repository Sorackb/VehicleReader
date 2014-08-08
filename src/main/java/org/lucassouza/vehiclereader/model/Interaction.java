package org.lucassouza.vehiclereader.model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Interaction {

  private VehicleClassification classification;
  private Document pageSource;
  private String url;
  private Integer referenceId;
  private Integer brandId;
  private String modelId;
  private Integer yearPriceId;
  private String eventValidation;
  private String viewState;

  private enum Event {

    REFERENCE("ScriptManager1|ddlTabelaReferencia", "ddlTabelaReferencia"),
    BRAND("UdtMarca|ddlMarca", "ddlMarca"),
    MODEL("updModelo|ddlModelo", "ddlModelo"),
    YEAR_PRICE("updAnoValor|ddlAnoValor", "ddlAnoValor");
    private final String scriptManager;
    private final String eventTarget;

    Event(String aScriptManager, String aEventTarget) {
      scriptManager = aScriptManager;
      eventTarget = aEventTarget;
    }

    public String getScriptManager() {
      return scriptManager;
    }

    public String getEventTarget() {
      return eventTarget;
    }
  }

  private void clearValues() {
    this.referenceId = 0;
    this.brandId = 0;
    this.modelId = "0";
    this.yearPriceId = 0;
    this.openPage();
  }

  private void openPage() {
    Connection pageConnection;
    Response pageResponse;

    if (this.classification != null) {
      try {
        this.url = "http://www.fipe.org.br/web/indices/veiculos/default.aspx?v="
                + this.classification.getComplement() + "&p=" + this.classification.getId();

        pageConnection = Jsoup.connect(this.url)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) "
                        + "AppleWebKit/537.36 (KHTML, like Gecko) "
                        + "Chrome/32.0.1700.76 "
                        + "Safari/537.36");
        pageResponse = pageConnection.method(Connection.Method.GET).execute();
        this.pageSource = pageResponse.parse();
        // Na primeira vez as tags de validação aparecem dentro de um componente "hidden"
        this.viewState = this.getElementAttr("#__VIEWSTATE", "value");
        this.eventValidation = this.getElementAttr("#__EVENTVALIDATION", "value");
        // Necessário já que para a primeira referência o post não deve ser acionado
        this.referenceId = Integer.parseInt(this.getElementAttr(
                "select#ddlTabelaReferencia > option[selected=selected]", "value"));
      } catch (IOException ex) {
        // Tenta novamente
        this.openPage();
      }
    }
  }

  private void loadPage(Event event) {
    Connection conexaoPagina;
    Response respostaPagina;

    if (this.classification != null) {
      try {
        conexaoPagina = Jsoup.connect(this.url)
                .data("ScriptManager1", event.getScriptManager())
                .data("__EVENTTARGET", event.getEventTarget())
                .data("__VIEWSTATE", this.viewState)
                .data("__EVENTVALIDATION", this.eventValidation)
                .data("ddlTabelaReferencia", this.referenceId.toString())
                .data("ddlMarca", this.brandId.toString())
                .data("ddlModelo", this.modelId)
                .data("ddlAnoValor", this.yearPriceId.toString())
                .data("__ASYNCPOST", "true")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) "
                        + "AppleWebKit/537.36 (KHTML, like Gecko) "
                        + "Chrome/32.0.1700.76 "
                        + "Safari/537.36");
        respostaPagina = conexaoPagina.method(Connection.Method.POST).execute();
        this.pageSource = respostaPagina.parse();
        // Atualiza as variáveis de sessão
        this.viewState = this.getVariable("__VIEWSTATE");
        this.eventValidation = this.getVariable("__EVENTVALIDATION");
      } catch (IOException ex) {
        // Tenta novamente
        this.loadPage(event);
      }
    }
  }

  public String getElementAttr(String cssSelector, String attr) {
    Element element = this.pageSource.select(cssSelector).first();
    String result = new String();

    if (element != null) {
      result = element.attr(attr);
    }

    return result;
  }

  /**
   * Busca uma variável, delimitada por um formato específico (|VARIAVEL|VALOR|)
   * no de retorno HTML
   *
   * @param variable identificador da variável a ser procurada
   * @return valor definido no HTML
   */
  public String getVariable(String variable) {
    String result = new String();
    String htmlText = this.pageSource.text();
    Integer startPosition;
    Integer endPosition;

    variable = "|" + variable + "|";
    startPosition = htmlText.indexOf(variable) + variable.length();

    if (startPosition > 0) {
      endPosition = htmlText.indexOf("|", startPosition);
      result = htmlText.substring(startPosition, endPosition);
    }

    return result;
  }
  
  public Document getPageSource() {
    return this.pageSource;
  }
  
  public void setClassification(VehicleClassification classification) {
    this.classification = classification;
    this.clearValues();
    this.openPage();
  }
  
  public void setReferenceId(Integer referenceId) {
    this.referenceId = referenceId;
    this.loadPage(Event.REFERENCE);
    /* Necessário porque se não for zerado, o serviço da fipe instancia com valor
     * nulo o objeto de retorno resultando em um erro
     */
    this.modelId = "0";
    this.yearPriceId = 0;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
    this.loadPage(Event.BRAND);
    /* Necessário porque se não for zerado, o serviço da fipe instancia com valor
     * nulo o objeto de retorno resultando em um erro
     */
    this.yearPriceId = 0;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
    this.loadPage(Event.MODEL);
  }

  public void setYearPriceId(Integer yearPriceId) {
    this.yearPriceId = yearPriceId;
    this.loadPage(Event.YEAR_PRICE);
  }
}
