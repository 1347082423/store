package com.ex.store.sys.service.impl;

import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.dto.TreeDto;
import com.ex.store.core.dto.UserDto;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.pojo.ExSysRoleResource;
import com.ex.store.core.util.CollectionUtils;
import com.ex.store.core.util.LongUtils;
import com.ex.store.core.util.TreeUtils;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.mapper.MenuMapper;
import com.ex.store.sys.mapper.PermissionsMapper;
import com.ex.store.sys.mapper.UserMapper;
import com.ex.store.sys.service.SysService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wex
 * @Date 2021-1-4 16:53
 * @Desc
 **/

@Service

public class SysServiceImpl implements SysService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PermissionsMapper permissionsMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<MenuDto> obtainMenu() {
        //获取List
        List<ExSysMenu> list = menuMapper.findList();
        final List<MenuDto> menus = new ArrayList<MenuDto>();
        list.stream().forEach(exSysMenu -> {
            menus.add(new MenuDto(exSysMenu.getTitle(), exSysMenu.getName(), exSysMenu.getIcon(), exSysMenu.getUrl(), exSysMenu.getId(), exSysMenu.getPid()));
        });
        //list转换为树形结构
        return TreeUtils.coverTree(menus);
    }

    @Override
    public PageAjaxResponse getAllMenu(PageParameter<ExSysMenu> pageParameter) {
        List<ExSysMenu> list = menuMapper.findListByPage(pageParameter);
        int count = menuMapper.findCount(pageParameter);
        PageAjaxResponse pageAjaxResponse = PageAjaxResponse.success(list, count);
        return pageAjaxResponse;
    }

    @Override
    public AjaxResponse getAllMenu() {
        //获取List
        List<ExSysMenu> list = menuMapper.findList();
        final List<TreeDto> treeDtos = new ArrayList<TreeDto>();
        list.stream().forEach(exSysMenu -> {
            treeDtos.add(new TreeDto(exSysMenu.getId(), exSysMenu.getPid(), exSysMenu.getTitle(), false, false));
        });
        //list转换为树形结构
        List<TreeDto> dtos = TreeUtils.coverTreeWithChildren(treeDtos);
        TreeDto treeDto = new TreeDto(0, 0, "根目录", true, false);
        treeDto.setChildren(dtos);
        return AjaxResponse.success(treeDto);
    }

    @Override
    public String saveMenu(ExSysMenu exSysMenu) {
        int count = 0;
        if (LongUtils.longIsNull(exSysMenu.getId())) {
            count = menuMapper.saveMenu(exSysMenu);
        } else {
            List<ExSysMenu> list = new ArrayList<ExSysMenu>();
            list.add(exSysMenu);
            count = menuMapper.updateByList(list);
        }
        String msg = "保存失败";
        if (count > 0 && exSysMenu.getId() == 0) {
            msg = "保存成功";
        } else if (count > 0 && exSysMenu.getId() != 0) {
            msg = "更新成功";
        }
        return msg;
    }

    @Override
    public String isForbid(List<ExSysMenu> exSysMenus) {
        exSysMenus.stream().forEach(exSysMenu -> {
            exSysMenu.setIsForbid(2);
        });
        int count = menuMapper.updateByList(exSysMenus);
        String msg = "保存失败";
        if (count > 0) {
            msg = "保存成功";
        }
        return msg;
    }

    @Override
    public PageAjaxResponse getAllPermissions(PageParameter<ExSysRole> pageParameter) {
        List<ExSysRole> list = permissionsMapper.findPermissionsListByPage(pageParameter);
        int count = permissionsMapper.findPermissionsCount(pageParameter);
        final List<RoleAndResource> roleAndResources = new ArrayList<RoleAndResource>(list.size());
        list.stream().forEach(exSysRole -> {
            RoleAndResource roleAndResource = new RoleAndResource();
            BeanUtils.copyProperties(exSysRole, roleAndResource);
            List<Map<String, Object>> permissionsMap = permissionsMapper.getPermissions(exSysRole.getId());
            roleAndResource.setIds(CollectionUtils.ListMapToString(permissionsMap, "id"));
            roleAndResource.setRoles(CollectionUtils.ListMapToString(permissionsMap, "title"));
            roleAndResources.add(roleAndResource);
        });
        PageAjaxResponse pageAjaxResponse = PageAjaxResponse.success(roleAndResources, count);
        return pageAjaxResponse;
    }

    @Override
    public AjaxResponse saveRoles(List<RoleAndResource> roleAndResources) {
        int count = permissionsMapper.saveRoles(roleAndResources);
        String msg = "数据更新失败";
        if (count > 0) {
            msg = "数据更新成功";
        }
        return AjaxResponse.success(msg);
    }

    @Override
    @Transactional
    public String insertRole(RoleAndResource exSysMenus) {
        String msg = "";
        int count = 0;
        //1、主表数据保存
        if (LongUtils.longIsNull(exSysMenus.getId())) {
            count = permissionsMapper.insertRole(exSysMenus);
        } else {
            List<RoleAndResource> list = new ArrayList<>(1);
            list.add(exSysMenus);
            count = permissionsMapper.saveRoles(list);
        }
        //2、关系表维护

        //新增权限
        List<Long> resourceIds = CollectionUtils.StringToList(exSysMenus.getIds());
        ExSysRoleResource exSysRoleResource1 = new ExSysRoleResource();
        exSysRoleResource1.setRoleid(exSysMenus.getId());
        int delCount = permissionsMapper.deletRoleResource(exSysRoleResource1);
        if (CollectionUtils.isNull(resourceIds)){
            return "更新成功";
        }
        List<ExSysRoleResource> list = new ArrayList<ExSysRoleResource>(resourceIds.size());
        resourceIds.stream().forEach(resourceId -> {
            ExSysRoleResource exSysRoleResource = new ExSysRoleResource();
            exSysRoleResource.setRoleid(exSysMenus.getId());
            exSysRoleResource.setResourceid(resourceId);
            list.add(exSysRoleResource);
        });
        int saveRoleResource = permissionsMapper.saveRoleResource(list);
        if (count > 0 && saveRoleResource == resourceIds.size()){
            msg = "更新成功";
        }else{
            throw new BusinessException("更新失败，角色入库" + count + "；资源关联成功" + saveRoleResource + "条；应关联" + resourceIds.size() + "条");
        }
        return msg;
    }

    @Override
    public PageAjaxResponse findUserByPage(PageParameter pageParameter) {
        List<UserDto> pageList = userMapper.findUserListByPage(pageParameter);
        int count = userMapper.findUserPageCount(pageParameter);
        return PageAjaxResponse.success(pageList,count);
    }
}
