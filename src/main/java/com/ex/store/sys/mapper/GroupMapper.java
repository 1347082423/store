package com.ex.store.sys.mapper;

import com.ex.store.core.pojo.ExSysGroup;
import com.ex.store.core.pojo.ExSysUserGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
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

    List<ExSysGroup> findGroupByCondition(ExSysGroup exSysGroup);

    int updateGroup(ArrayList<ExSysGroup> exSysGroups);

    int insertGroup(ExSysGroup exSysGroup);
}
