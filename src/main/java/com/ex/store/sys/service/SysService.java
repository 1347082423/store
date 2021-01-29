package com.ex.store.sys.service;

import com.ex.store.core.dto.*;
import com.ex.store.core.pojo.*;
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

    List<TreeDto> getGroupByTree(ExSysGroup exSysGroup);

    AjaxResponse updateGroup(ArrayList<ExSysGroup> exSysGroups);

    String insertGroup(ExSysGroup exSysGroup);

    PageAjaxResponse findResourceByPageCondition(PageParameter<ExSysResource> pageParameter);

    String saveResource(ExSysResource exSysResource);

    String updateResource(ArrayList<ExSysResource> exSysResources);

    List<DictionaryDto> findDictionaryByCondition(DictionaryDto dictionaryDto);

    AjaxResponse updateDictionary(ArrayList<DictionaryDto> exSysDictionaries);

    String insertDictionary(DictionaryDto exSysGroup);

    List<DictionaryDto> getChilds(DictionaryDto dictionaryDto);
}
