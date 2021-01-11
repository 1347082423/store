package com.ex.store.core.pojo;


public class ExSysRole {

  private Long id;
  private String name;
  private String rolename;
  private String desc;
  private Long type;
  private Long isForbid;



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
  }


  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
  }

  public Long getIsForbid() {
    return isForbid;
  }

  public void setIsForbid(Long isForbid) {
    this.isForbid = isForbid;
  }
}
