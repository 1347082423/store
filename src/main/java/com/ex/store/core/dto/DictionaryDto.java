package com.ex.store.core.dto;

import com.ex.store.core.pojo.ExSysDictionary;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-29 11:44
 * @Desc
 **/
public class DictionaryDto extends ExSysDictionary {

    private String pName;

    private List<DictionaryDto> childs;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<DictionaryDto> getChilds() {
        return childs;
    }

    public void setChilds(List<DictionaryDto> childs) {
        this.childs = childs;
    }
}
