package org.lucassouza.vehiclereader.model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.lucassouza.navigation.model.Content;
import org.lucassouza.navigation.model.Navigator;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author lucas.souza
 */
public class Interaction extends Navigator {

  private static Interaction instance;
  private final LinkedHashMap<String, String> headers;
  private final String domain;
  private Response lastResponse;

  public Interaction() {
    super();
    this.headers = new LinkedHashMap<>();
    this.domain = "http://veiculos.fipe.org.br/";
    this.defaults.domain(this.domain + "api/veiculos/");
    this.headers.put("Referer", this.domain);
    this.headers.put("Content-Type", "application/x-www-form-urlencoded");
  }

  public static Interaction getInstance() {
    if (instance == null) {
      instance = new Interaction();
    }

    return instance;
  }

  public void setClassification(VehicleClassification classification) throws IOException,
          InterruptedException, ExecutionException {
    Content references;

    this.fields.put("codigoTipoVeiculo", String.valueOf(classification.getId()));

    references = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarTabelaDeReferencia")
            .headers(this.headers)
            .build();

    this.lastResponse = this.request(references).join();
  }

  public void setReferenceId(int id) throws IOException, InterruptedException,
          ExecutionException {
    Content brands;

    this.fields.put("codigoTabelaReferencia", String.valueOf(id));

    brands = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarMarcas")
            .fields("codigoTipoVeiculo",
                    "codigoTabelaReferencia")
            .headers(this.headers)
            .build();

    this.lastResponse = this.request(brands).join();
  }

  public void setBrandId(int id) throws IOException, InterruptedException,
          ExecutionException {
    Content models;

    this.fields.put("codigoMarca", String.valueOf(id));

    models = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarModelos")
            .fields("codigoTipoVeiculo",
                    "codigoTabelaReferencia",
                    "codigoMarca")
            .headers(this.headers)
            .build();

    this.lastResponse = this.request(models).join();
  }

  public void setModelId(int id) throws IOException, InterruptedException, ExecutionException {
    Content years;

    this.fields.put("codigoModelo", String.valueOf(id));
    this.cookies.clear(); // A partir de 100 feitos com o mesmo cookie ocasionam um erro

    years = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarAnoModelo")
            .fields("codigoTipoVeiculo",
                    "codigoTabelaReferencia",
                    "codigoMarca",
                    "codigoModelo")
            .headers(this.headers)
            .build();

    this.lastResponse = this.request(years).join();
  }

  public void setYearPriceId(String id) throws IOException, InterruptedException,
          ExecutionException {
    Content informations;
    String[] parts;

    parts = id.split("-");
    this.fields.put("anoModelo", parts[0].trim());
    this.fields.put("codigoTipoCombustivel", parts[1].trim());
    this.fields.put("tipoConsulta", "tradicional");

    informations = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarValorComTodosParametros")
            .fields("codigoTipoVeiculo",
                    "codigoTabelaReferencia",
                    "codigoMarca",
                    "codigoModelo",
                    "anoModelo",
                    "codigoTipoCombustivel",
                    "tipoConsulta")
            .headers(this.headers)
            .build();

    this.lastResponse = this.request(informations).join();
  }

  public Response getLastResponse() {
    return lastResponse;
  }
}
