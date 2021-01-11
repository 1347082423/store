package com.ex.store.sys.service.impl;

import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.dto.TreeDto;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.util.LongUtils;
import com.ex.store.core.util.TreeUtils;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.mapper.MenuMapper;
import com.ex.store.sys.mapper.PermissionsMapper;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MenuDto> obtainMenu() {
        //获取List
        List<ExSysMenu> list = menuMapper.findList();
         final List<MenuDto> menus = new ArrayList<MenuDto>();
         list.stream().forEach(exSysMenu -> {
             menus.add(new MenuDto(exSysMenu.getTitle(),exSysMenu.getName(),exSysMenu.getIcon(),exSysMenu.getUrl(),exSysMenu.getId(),exSysMenu.getPid()));
         });
        //list转换为树形结构
        return TreeUtils.coverTree(menus);
    }

    @Override
    public PageAjaxResponse getAllMenu(PageParameter<ExSysMenu> pageParameter) {
        List<ExSysMenu> list = menuMapper.findListByPage(pageParameter);
        int count = menuMapper.findCount(pageParameter);
        PageAjaxResponse pageAjaxResponse = PageAjaxResponse.success(list,count);
        return pageAjaxResponse;
    }

    @Override
    public AjaxResponse getAllMenu() {
        //获取List
        List<ExSysMenu> list = menuMapper.findList();
        final List<TreeDto> treeDtos = new ArrayList<TreeDto>();
        list.stream().forEach(exSysMenu -> {
            treeDtos.add(new TreeDto(exSysMenu.getId(),exSysMenu.getPid(),exSysMenu.getTitle(),false,false));
        });
        //list转换为树形结构
        List<TreeDto> dtos = TreeUtils.coverTreeWithChildren(treeDtos);
        TreeDto treeDto = new TreeDto(0,0,"根目录",true,false);
        treeDto.setChildren(dtos);
        return AjaxResponse.success(treeDto);
    }

    @Override
    public String saveMenu(ExSysMenu exSysMenu) {
        int count = 0;
        if (LongUtils.intIsNull(exSysMenu.getId())){
            count = menuMapper.saveMenu(exSysMenu);
        }else{
            List<ExSysMenu> list = new ArrayList<ExSysMenu>();
            list.add(exSysMenu);
            count = menuMapper.updateByList(list);
        }
        String msg = "保存失败";
        if (count > 0 && exSysMenu.getId() == 0){
            msg = "保存成功";
        }else if (count > 0 && exSysMenu.getId() != 0){
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
        if (count > 0){
            msg = "保存成功";
        }
        return msg;
    }

    @Override
    public PageAjaxResponse getAllPermissions(PageParameter<ExSysRole> pageParameter) {
        List<ExSysMenu> list = permissionsMapper.findPermissionsListByPage(pageParameter);
        int count = permissionsMapper.findPermissionsCount(pageParameter);
        PageAjaxResponse pageAjaxResponse = PageAjaxResponse.success(list,count);
        return pageAjaxResponse;
    }
}
