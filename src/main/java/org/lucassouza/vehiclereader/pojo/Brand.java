package org.lucassouza.vehiclereader.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Customizer;
import org.lucassouza.vehiclereader.model.customizer.BrandCT;

/**
 *
 * @author Lucas Souza [sorackb@gmail.com]
 */
@Entity
@Table(name = "fipe.tb_brand")
@Customizer(BrandCT.class)
public class Brand implements Serializable {
  @Id
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
