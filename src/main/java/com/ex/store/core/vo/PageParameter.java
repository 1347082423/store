package com.ex.store.core.vo;

/**
 * @Author wex
 * @Date 2021-1-6 10:55
 * @Desc
 **/
public class PageParameter<T> {
    private int page;
    private int limit;
    private T parameter;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }
}
