package se.systementor.supershoppen1.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Category {
    private String name;
    private String description;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
      }
    
      public void setId(Integer id) {
        this.id = id;
      }

      public void setName(String v)
      {
          name = v;
      }
  

      public String getName()
      {
          return name;
      }


    public void setDescription(String string) {
        description = string;
    }
    public String getDescription() {
        return description;
    }

    
}

