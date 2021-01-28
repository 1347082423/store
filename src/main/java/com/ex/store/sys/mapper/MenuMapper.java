package com.ex.store.sys.mapper;

import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysUser;
import com.ex.store.core.vo.PageParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-4 17:02
 * @Desc
 **/

@Mapper
public interface MenuMapper {

    List<ExSysMenu> findList();

    List<ExSysMenu> findListByPage(PageParameter<ExSysMenu> pageParameter);

    int findCount(PageParameter<ExSysMenu> pageParameter);

    int saveMenu(ExSysMenu exSysMenu);

    int updateByList(List<ExSysMenu> exSysMenus);
}
