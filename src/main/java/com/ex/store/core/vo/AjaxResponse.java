package com.ex.store.core.vo;

import java.io.Serializable;

/**
 * @Describe
 * @Author wex
 * @Date 2020-9-12 12:54
 * @Version
 **/
public class AjaxResponse implements Serializable {
    private String msg;
    private boolean isOk;
    private int code;//网络状态码
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    /**
     *
     * 创建一个空的 AjaxResponse对象
     * @return com.ex.comm.vo.AjaxResponse
     */
    public static AjaxResponse success(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(0);
        return ajaxResponse;
    }

    public static AjaxResponse success(Object data){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(0);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    public static AjaxResponse success(String msg){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(0);
        ajaxResponse.setMsg(msg);
        return ajaxResponse;
    }
    public static AjaxResponse success(String msg,Object data){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(0);
        ajaxResponse.setMsg(msg);
        ajaxResponse.setData(data);
        return ajaxResponse;
    }

    public static AjaxResponse error(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(false);
        ajaxResponse.setCode(1);
        return ajaxResponse;
    }
}
