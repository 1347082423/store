package com.ex.store.core.pojo;


import java.util.Date;

public class ExSysUploadTemplate {

  private long id;
  private String title;
  private String path;
  private String modelnmae;
  private String modeltype;
  private String mapper;
  private Date time;
  private String mapperid;
  private String mapperidlist;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


  public String getModelnmae() {
    return modelnmae;
  }

  public void setModelnmae(String modelnmae) {
    this.modelnmae = modelnmae;
  }


  public String getModeltype() {
    return modeltype;
  }

  public void setModeltype(String modeltype) {
    this.modeltype = modeltype;
  }


  public String getMapper() {
    return mapper;
  }

  public void setMapper(String mapper) {
    this.mapper = mapper;
  }


  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getMapperid() {
    return mapperid;
  }

  public void setMapperid(String mapperid) {
    this.mapperid = mapperid;
  }

  public String getMapperidlist() {
    return mapperidlist;
  }

  public void setMapperidlist(String mapperidlist) {
    this.mapperidlist = mapperidlist;
  }
}
