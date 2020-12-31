package com.ex.store.core.pojo;


public class ExSysResource {

  private String name;
  private String category;
  private String url;
  private long id;
  private String desc;
  private long isForbid;
  private String model;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }


  public long getIsForbid() {
    return isForbid;
  }

  public void setIsForbid(long isForbid) {
    this.isForbid = isForbid;
  }


  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

}
