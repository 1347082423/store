package com.ex.store.core.dto;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-7 14:04
 * @Desc
 **/
public class TreeDto {
    private long id;
    private String name;
    private boolean open;
    private boolean checked;
    private List<TreeDto> children;
    private long pid;

    public TreeDto() {
    }

    public TreeDto(long id,long pid, String name, boolean open, boolean checked) {
        this.id = id;
        this.name = name;
        this.open = open;
        this.checked = checked;
        this.pid = pid;
    }

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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeDto> children) {
        this.children = children;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
