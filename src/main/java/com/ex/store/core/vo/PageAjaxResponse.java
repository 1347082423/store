package com.ex.store.core.vo;

import java.io.Serializable;

/**
 * @Describe
 * @Author wex
 * @Date 2020-9-12 12:54
 * @Version
 **/
public class PageAjaxResponse implements Serializable {
    private String msg;
    private boolean isOk;
    private int code;//网络状态码
    private Object data;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static PageAjaxResponse success(Object data,int count){
        PageAjaxResponse pageAjaxResponse = new PageAjaxResponse();
        pageAjaxResponse.setOk(true);
        pageAjaxResponse.setCode(0);
        pageAjaxResponse.setCount(count);
        pageAjaxResponse.setData(data);
        return pageAjaxResponse;
    }


}
