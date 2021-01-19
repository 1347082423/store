package com.ex.store.sys.mapper;

import com.ex.store.core.pojo.ExSysUserGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-19 9:21
 * @Desc
 **/
@Mapper
public interface GroupMapper {

    int updateUserAndGroupByUserId(List<ExSysUserGroup> list);

    int deletGroupUser (ExSysUserGroup exSysUserGroup);
}
