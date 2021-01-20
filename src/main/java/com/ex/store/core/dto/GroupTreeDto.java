package com.ex.store.core.dto;

/**
 * @Author wex
 * @Date 2021-1-20 11:11
 * @Desc
 **/
public class GroupTreeDto extends TreeDto {
    private Long type;

    public GroupTreeDto() {
    }

    public GroupTreeDto(long id, long pid, String name, boolean open, boolean checked, Long type) {
        super(id, pid, name, open, checked);
        this.type = type;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
