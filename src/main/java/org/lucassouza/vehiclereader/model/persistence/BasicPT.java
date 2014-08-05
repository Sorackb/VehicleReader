package org.lucassouza.vehiclereader.model.persistence;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.lucassouza.tools.PropertyTool;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 * @param <A> Classe utilizada para persistir
 */
public class BasicPT<A> {

  protected EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;
  protected Class<A> referencedClass;

  public BasicPT() {
    PropertyTool iniFile = new PropertyTool();
    HashMap<String, String> properties = new HashMap<>();
    String dataBaseLocation;
    String systemPath = this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath();
    File system = new File(systemPath);

    try {
      if (system.getParent().contains("target")) {
        iniFile.readPropertyFile("C:/virtual/LeitorFipeHTTP/config.ini");
      } else {
        iniFile.readPropertyFile(system.getParent() + "/config.ini");
      }
    } catch (IOException ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    dataBaseLocation = "jdbc:sqlserver://" + iniFile.getProperty("conexao.host") + ";"
            + "databaseName=" + iniFile.getProperty("conexao.database");
    properties.put("javax.persistence.jdbc.url", dataBaseLocation);
    properties.put("javax.persistence.jdbc.user", iniFile.getProperty("conexao.usuario"));
    properties.put("javax.persistence.jdbc.password", iniFile.getProperty("conexao.senha"));
    //Inicializa a Persistence Unit
    this.entityManagerFactory = Persistence.createEntityManagerFactory(
            "VehicleReaderPU", properties);
    //Cria um manager para manipulação do BD
    this.entityManager = this.entityManagerFactory.createEntityManager();
  }

  public void insert(A object) {
    this.entityManager.getTransaction().begin();
    this.entityManager.persist(object);
    this.entityManager.getTransaction().commit();
  }

  public void insert(List<A> objectList) {
    this.entityManager.getTransaction().begin();

    for (A object : objectList) {
      this.entityManager.persist(object);
    }

    this.entityManager.getTransaction().commit();
  }

  public void update(A objet) {
    this.entityManager.getTransaction().begin();
    this.entityManager.merge(objet);
    this.entityManager.getTransaction().commit();
  }

  public A search(Object id) {
    return this.entityManager.find(this.referencedClass, id);
  }

  @SuppressWarnings("unchecked")
  public A search(LinkedHashMap<String, Object> condition, LinkedHashMap<String, String> orderBy) {
    String sql = this.setQuery(condition, orderBy);
    List<A> queryResult;
    Query query;

    query = this.entityManager.createQuery(sql);
    queryResult = query.setFirstResult(0).setMaxResults(1).getResultList();

    if (!queryResult.isEmpty()) {
      return queryResult.get(0);
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public List<A> searchList(LinkedHashMap<String, Object> condition,
          LinkedHashMap<String, String> orderBy) {
    String sql = this.setQuery(condition, orderBy);
    List<A> result;
    Query query;

    query = this.entityManager.createQuery(sql);
    // Preenche os parâmetros
    this.fillParameters(query, condition);
    result = query.getResultList();

    return result;
  }

  @SuppressWarnings("unchecked")
  public List<A> searchAll(LinkedHashMap<String, String> orderBy) {
    String sql = this.setQuery(null, orderBy);
    List<A> resultado;
    Query query;

    query = this.entityManager.createQuery(sql);
    resultado = query.getResultList();

    return resultado;
  }

  protected String setQuery(LinkedHashMap<String, Object> condition,
          LinkedHashMap<String, String> orderBy) {
    String result = "select x from " + this.referencedClass.getSimpleName() + " x";
    String queryCondition = this.setCondition(condition);
    String queryOrderBy = this.setOrderBy(orderBy);

    if (!queryCondition.equals("")) {
      result = result + " " + queryCondition;
    }

    if (!queryOrderBy.equals("")) {
      result = result + " " + queryOrderBy;
    }

    return result;
  }

  protected void fillParameters(Query query, LinkedHashMap<String, Object> condition) {
    if (condition != null && !condition.isEmpty()) {
      for (String key : condition.keySet()) {
        query.setParameter("p" + key.toLowerCase(), condition.get(key));
      }
    }
  }

  protected String setCondition(LinkedHashMap<String, Object> condition) {
    StringBuilder result = new StringBuilder();

    if (condition != null && !condition.isEmpty()) {
      for (String key : condition.keySet()) {
        if (result.length() != 0) {
          result.append(" and ");
        }

        result.append("x.");
        result.append(key);
        result.append(" = :p");
        result.append(key.toLowerCase());
      }

      return "where " + result.toString();
    }

    return result.toString();
  }

  protected String setOrderBy(LinkedHashMap<String, String> orderBy) {
    StringBuilder result = new StringBuilder();

    if (orderBy != null && !orderBy.isEmpty()) {
      for (String key : orderBy.keySet()) {
        String content = orderBy.get(key);

        if (result.length() != 0) {
          result.append(", ");
        }

        result.append("x.");
        result.append(key);

        if (content != null && !content.equals("")) {
          result.append(" ");
          result.append(content);
        }
      }

      return "order by " + result.toString();
    }

    return result.toString();
  }
}
