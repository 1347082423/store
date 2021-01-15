package com.ex.store.core.dto;

import com.ex.store.core.pojo.ExSysUser;

/**
 * @Author wex
 * @Date 2021-1-15 15:25
 * @Desc
 **/
public class UserDto extends ExSysUser {

    private String groupNames;
    private String groupIds;

    private String roleNames;

    private String roleIds;

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
