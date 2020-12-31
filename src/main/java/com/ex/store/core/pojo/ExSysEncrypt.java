package com.ex.store.core.pojo;


public class ExSysEncrypt {

  private long id;
  private long fileid;
  private long encryptindex;
  private String encryptcontent;

  public ExSysEncrypt() {
  }

  public ExSysEncrypt(long fileid, long encryptindex, String encryptcontent) {
    this.fileid = fileid;
    this.encryptindex = encryptindex;
    this.encryptcontent = encryptcontent;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getFileid() {
    return fileid;
  }

  public void setFileid(long fileid) {
    this.fileid = fileid;
  }


  public long getEncryptindex() {
    return encryptindex;
  }

  public void setEncryptindex(long encryptindex) {
    this.encryptindex = encryptindex;
  }


  public String getEncryptcontent() {
    return encryptcontent;
  }

  public void setEncryptcontent(String encryptcontent) {
    this.encryptcontent = encryptcontent;
  }

}
