package com.ex.store.core.dto;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-5 10:37
 * @Desc
 **/
public class MenuDto {
    private String title;
    private String name;
    private String icon;
    private List<MenuDto> list;
    private String jump;
    private Long id;
    private Long pid;

    public MenuDto() {
    }

    public MenuDto(String title, String name, String icon, String jump, Long id, Long pid) {
        this.title = title;
        this.name = name;
        this.icon = icon;
        this.jump = jump;
        this.id = id;
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuDto> getList() {
        return list;
    }

    public void setList(List<MenuDto> list) {
        this.list = list;
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
