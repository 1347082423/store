package com.ex.store.core.pojo;


import java.util.Date;

public class ExSysDictionary {

  private Long dicId;
  private String dicKey;
  private String dicValue;
  private Long dicParentid;
  private Date dicCreateTime;
  private Date dicUpdateTime;
  private String dicCategory;
  private String dicIsStair;
  private String dicSort;
  private String dicDesc;
  private Long dicForbid;
  private Long dicLeaf;



  public Long getDicId() {
    return dicId;
  }

  public void setDicId(Long dicId) {
    this.dicId = dicId;
  }

  public String getDicKey() {
    return dicKey;
  }

  public void setDicKey(String dicKey) {
    this.dicKey = dicKey;
  }

  public String getDicValue() {
    return dicValue;
  }

  public void setDicValue(String dicValue) {
    this.dicValue = dicValue;
  }

  public Long getDicParentid() {
    return dicParentid;
  }

  public void setDicParentid(Long dicParentid) {
    this.dicParentid = dicParentid;
  }

  public Date getDicCreateTime() {
    return dicCreateTime;
  }

  public void setDicCreateTime(Date dicCreateTime) {
    this.dicCreateTime = dicCreateTime;
  }

  public Date getDicUpdateTime() {
    return dicUpdateTime;
  }

  public void setDicUpdateTime(Date dicUpdateTime) {
    this.dicUpdateTime = dicUpdateTime;
  }

  public String getDicCategory() {
    return dicCategory;
  }

  public void setDicCategory(String dicCategory) {
    this.dicCategory = dicCategory;
  }

  public String getDicIsStair() {
    return dicIsStair;
  }

  public void setDicIsStair(String dicIsStair) {
    this.dicIsStair = dicIsStair;
  }

  public String getDicSort() {
    return dicSort;
  }

  public void setDicSort(String dicSort) {
    this.dicSort = dicSort;
  }

  public String getDicDesc() {
    return dicDesc;
  }

  public void setDicDesc(String dicDesc) {
    this.dicDesc = dicDesc;
  }

  public Long getDicForbid() {
    return dicForbid;
  }

  public void setDicForbid(Long dicForbid) {
    this.dicForbid = dicForbid;
  }

  public Long getDicLeaf() {
    return dicLeaf;
  }

  public void setDicLeaf(Long dicLeaf) {
    this.dicLeaf = dicLeaf;
  }
}
