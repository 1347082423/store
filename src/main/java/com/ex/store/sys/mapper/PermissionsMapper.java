package com.ex.store.sys.mapper;

import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.vo.PageParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-11 15:35
 * @Desc
 **/
@Mapper
public interface PermissionsMapper {
    List<ExSysMenu> findPermissionsListByPage(PageParameter<ExSysRole> pageParameter);

    int findPermissionsCount(PageParameter<ExSysRole> pageParameter);
}
