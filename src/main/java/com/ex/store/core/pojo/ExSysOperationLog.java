package com.ex.store.core.pojo;


import java.util.Date;

public class ExSysOperationLog {

  private long id;
  private String name;
  private String tableName;
  private String tableId;
  private String type;
  private String operatorId;
  private String operatorName;
  private Date operationTime;
  private String modelName;
  private long operatorResult;
  private String operatorParam;
  private String operatorSql;
  private String interceptType;


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


  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getOperatorId() {
    return operatorId;
  }

  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }


  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public Date getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Date operationTime) {
    this.operationTime = operationTime;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }


  public long getOperatorResult() {
    return operatorResult;
  }

  public void setOperatorResult(long operatorResult) {
    this.operatorResult = operatorResult;
  }


  public String getOperatorParam() {
    return operatorParam;
  }

  public void setOperatorParam(String operatorParam) {
    this.operatorParam = operatorParam;
  }


  public String getOperatorSql() {
    return operatorSql;
  }

  public void setOperatorSql(String operatorSql) {
    this.operatorSql = operatorSql;
  }


  public String getInterceptType() {
    return interceptType;
  }

  public void setInterceptType(String interceptType) {
    this.interceptType = interceptType;
  }

}
