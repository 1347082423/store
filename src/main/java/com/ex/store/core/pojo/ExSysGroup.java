package com.ex.store.core.pojo;


public class ExSysGroup {

  private Long id;
  private String name;
  private String groupfunction;
  private Long pid;
  private Long type;
  private Long isForbid;
  private Long minuser;
  private Long maxuser;
  private Long sort;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupfunction() {
    return groupfunction;
  }

  public void setGroupfunction(String groupfunction) {
    this.groupfunction = groupfunction;
  }

  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
  }

  public Long getMinuser() {
    return minuser;
  }

  public void setMinuser(Long minuser) {
    this.minuser = minuser;
  }

  public Long getMaxuser() {
    return maxuser;
  }

  public void setMaxuser(Long maxuser) {
    this.maxuser = maxuser;
  }

  public Long getIsForbid() {
    return isForbid;
  }

  public void setIsForbid(Long isForbid) {
    this.isForbid = isForbid;
  }

  public Long getSort() {
    return sort;
  }

  public void setSort(Long sort) {
    this.sort = sort;
  }
}
