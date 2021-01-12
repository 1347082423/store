package com.ex.store.core.dto;

import com.ex.store.core.pojo.ExSysRole;

/**
 * @Author wex
 * @Date 2021-1-12 11:20
 * @Desc
 **/
public class RoleAndResource extends ExSysRole {
    private String ids;
    private String roles;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
