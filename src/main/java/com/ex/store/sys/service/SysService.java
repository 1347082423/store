package com.ex.store.sys.service;

import com.ex.store.core.dto.GroupTreeDto;
import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.dto.UserDto;
import com.ex.store.core.pojo.ExSysGroup;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-4 16:53
 * @Desc
 **/
public interface SysService {
    List<MenuDto> obtainMenu();

    PageAjaxResponse getAllMenu(PageParameter<ExSysMenu> pageParameter);

    AjaxResponse getAllMenu();

    String saveMenu(ExSysMenu exSysMenu);

    String isForbid(List<ExSysMenu> exSysMenus);

    PageAjaxResponse getAllPermissions(PageParameter<ExSysRole> pageParameter);

    AjaxResponse saveRoles(List<RoleAndResource> roleAndResources);

    String insertRole(RoleAndResource exSysMenus);

    PageAjaxResponse findUserByPage(PageParameter pageParameter);

    AjaxResponse delUser(ArrayList<UserDto> userDtos);

    String insertUser(UserDto userDto);

    List<ExSysGroup> getAllGroup(ExSysGroup exSysGroup);
}
