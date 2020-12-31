package com.ex.store.core.pojo;


import java.util.Date;

public class ExSysFile {

  private long id;
  private String name;
  private String filename;
  private String path;
  private String type;
  private String suffix;
  private Date uploadtime;
  private String filedegree;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }


  public Date getUploadtime() {
    return uploadtime;
  }

  public void setUploadtime(Date uploadtime) {
    this.uploadtime = uploadtime;
  }

  public String getFiledegree() {
    return filedegree;
  }

  public void setFiledegree(String filedegree) {
    this.filedegree = filedegree;
  }

}
