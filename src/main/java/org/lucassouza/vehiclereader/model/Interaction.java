package org.lucassouza.vehiclereader.model;

import java.io.IOException;
import java.util.LinkedHashMap;
import org.jsoup.Connection;
import org.lucassouza.navigation.model.Content;
import org.lucassouza.navigation.model.Navigation;
import org.lucassouza.vehiclereader.type.FuelType;
import org.lucassouza.vehiclereader.type.VehicleClassification;

/**
 *
 * @author lucas.souza
 */
public class Interaction extends Navigation {

  private final LinkedHashMap<String, String> headers;
  private final String domain;

  public Interaction() {
    super();
    this.headers = new LinkedHashMap<>();
    this.domain = "http://veiculos.fipe.org.br/";
    this.defaults = Content.initializer()
            .domain(this.domain + "api/veiculos/");
    this.headers.put("Referer", this.domain);
    this.headers.put("Content-Type", "application/x-www-form-urlencoded");
  }

  public void setClassification(VehicleClassification classification) throws IOException {
    Content references;

    this.fields.put("codigoTipoVeiculo", String.valueOf(classification.getIdDB()));
    references = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarTabelaDeReferencia")
            .headers(this.headers)
            .build();

    this.request(references);
  }

  public void setReferenceId(int id) throws IOException {
    Content brands;

    this.fields.put("codigoTabelaReferencia", String.valueOf(id));

    brands = this.defaults.initialize()
            .method(Connection.Method.POST)
            .complement("/ConsultarMarcas")
            .fields("codigoTipoVeiculo",
                    "codigoTabelaReferencia")
            .headers(this.headers)
            .build();

    this.request(brands);
  }

  public void setBrandId(int id) throws IOException {
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

    this.request(models);
  }

  public void setModelId(int id) throws IOException {
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

    this.request(years);
  }

  public void setYearPriceId(String id) throws IOException {
    Content informations;
    String[] parts;

    parts = id.split("-");
    this.fields.put("anoModelo", parts[0].trim());
    this.fields.put("codigoTipoCombustivel", parts[1].trim());    
    this.fields.put("tipoConsulta", "tradicional");
    //this.fields.put("tipoVeiculo", "carro");

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

    this.request(informations);
  }
}
