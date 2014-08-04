package org.lucassouza.vehiclereader.pojo;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
public class Brand {
  private Integer id;
  private String description;
  private static final long serialVersionUID = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public Boolean equals(Brand brand) {
    return brand != null && brand.getId().equals(this.getId());
  }
}
