package com.ex.store.sys.service.impl;

import com.ex.store.core.cache.DictionaryCache;
import com.ex.store.core.dto.*;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.pojo.*;
import com.ex.store.core.util.CollectionUtils;
import com.ex.store.core.util.LongUtils;
import com.ex.store.core.util.StringUtils;
import com.ex.store.core.util.TreeUtils;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.mapper.*;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;


    @Override
    public List<MenuDto> obtainMenu() {
        //获取List
        List<ExSysMenu> list = menuMapper.findList();
        List<MenuDto> menus = new ArrayList<MenuDto>();
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
        List<RoleAndResource> roleAndResources = new ArrayList<RoleAndResource>(list.size());
        list.stream().forEach(exSysRole -> {
            RoleAndResource roleAndResource = new RoleAndResource();
            BeanUtils.copyProperties(exSysRole, roleAndResource);
            if (exSysRole.getType() == 1){
                List<Map<String, Object>> permissionsMap = permissionsMapper.getPermissions(exSysRole.getId());
                roleAndResource.setIds(CollectionUtils.ListMapToString(permissionsMap, "id"));
                roleAndResource.setRoles(CollectionUtils.ListMapToString(permissionsMap, "title"));
                roleAndResources.add(roleAndResource);
            }else if (exSysRole.getType() == 2){
                List<Map<String, Object>> permissionsMap = permissionsMapper.getResource(exSysRole.getId());
                roleAndResource.setIds(CollectionUtils.ListMapToString(permissionsMap, "id"));
                roleAndResource.setRoles(CollectionUtils.ListMapToString(permissionsMap, "name"));
                roleAndResources.add(roleAndResource);
            }

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
        //
        if (CollectionUtils.isNull(pageList)){
            return PageAjaxResponse.success(pageList,count);
        }
        for (UserDto userDto : pageList) {
            List<ExSysRole> exSysRoles = userMapper.loadRoleByUserId(userDto.getId());
            String roleNames = String.join(",", exSysRoles.stream().map(t -> t.getName()).distinct().collect(Collectors.toList()));
            List<Long> collect = exSysRoles.stream().map(t -> t.getId()).distinct().collect(Collectors.toList());
            userDto.setRoleNames(roleNames);
            String idsString = org.thymeleaf.util.StringUtils.join(collect, ",");
            userDto.setRoleIds(idsString);
            List<Map<String, Object>> maps = userMapper.loadGroupByUserId(userDto.getId());
            userDto.setGroupIds(CollectionUtils.ListMapToString(maps, "id"));
            userDto.setGroupNames(CollectionUtils.ListMapToString(maps, "name"));
        }

        return PageAjaxResponse.success(pageList,count);
    }

    @Override
    public AjaxResponse delUser(ArrayList<UserDto> userDtos) {
        //更新主表信息
        int count = userMapper.updateUserByList(userDtos);
        String msg = "数据保存失败";
        if (count > 0){
            msg = "数据保存成功";
        }
        return AjaxResponse.success(msg);
    }

    @Override
    @Transactional
    public String insertUser(UserDto userDto) {
        int count = 0;
        String msg = "数据更新失败";
        if (LongUtils.longIsNull(userDto.getId())){
            ExSysUser exSysUser = new ExSysUser();
            BeanUtils.copyProperties(userDto,exSysUser);
            count = userMapper.insertUser(exSysUser);
            userDto.setId(exSysUser.getId());
        }else{
            List<UserDto> userDtos = new ArrayList<UserDto>(1);
            userDtos.add(userDto);
            count = userMapper.updateUserByList(userDtos);
        }
        //维护组关系
        if (StringUtils.isNotNull(userDto.getGroupIds()) && count != 0 && !LongUtils.longIsNull(userDto.getId())){
            String ids = userDto.getGroupIds();
            List<Long> longList = CollectionUtils.StringToList(ids);
            List<ExSysUserGroup> groupList = new ArrayList<ExSysUserGroup>(longList.size());
            //删除依赖的组关系
            ExSysUserGroup exSysUserGroup = new ExSysUserGroup();
            exSysUserGroup.setUserid(userDto.getId());
            groupMapper.deletGroupUser(exSysUserGroup);
            longList.stream().forEach(l->{
                ExSysUserGroup exSysUserGroup1 = new ExSysUserGroup();
                exSysUserGroup1.setUserid(userDto.getId());
                exSysUserGroup1.setGroupid(l);
                groupList.add(exSysUserGroup1);
            });
            groupMapper.updateUserAndGroupByUserId(groupList);
        }
        if (StringUtils.isNotNull(userDto.getRoleIds()) && count != 0 && !LongUtils.longIsNull(userDto.getId())){
            String ids = userDto.getRoleIds();
            List<Long> longList = CollectionUtils.StringToList(ids);
            List<ExSysUserRole> roleList = new ArrayList<ExSysUserRole>(longList.size());
            ExSysUserRole exSysUserRole = new ExSysUserRole();
            exSysUserRole.setUserid(userDto.getId());
            permissionsMapper.deletRoleUser(exSysUserRole);
            longList.stream().forEach(l->{
                ExSysUserRole sysUserRole = new ExSysUserRole();
                sysUserRole.setUserid(userDto.getId());
                sysUserRole.setRoleid(l);
                roleList.add(sysUserRole);
            });
            permissionsMapper.updateUserAndRoleByUserId(roleList);
        }
        if (count != 0){
            msg = "数据更新成功";
        }
        return msg;
    }

    @Override
    public List<ExSysGroup> getAllGroup(ExSysGroup exSysGroup) {
        List<ExSysGroup> exSysGroups = groupMapper.findGroupByCondition(exSysGroup);
        return exSysGroups;
    }

    @Override
    public List<TreeDto> getGroupByTree(ExSysGroup exSysGroup) {
        List<ExSysGroup> exSysGroups = groupMapper.findGroupByCondition(exSysGroup);
        List<TreeDto> treeDtos = new ArrayList<TreeDto>(exSysGroups.size());
        exSysGroups.stream().forEach(exSysGroup1 -> {
            treeDtos.add(new TreeDto(exSysGroup1.getId(), exSysGroup1.getPid(), exSysGroup1.getName(), false, false));
        });
        //list转换为树形结构
        List<TreeDto> dtos = TreeUtils.coverTreeWithChildren(treeDtos);
        dtos.get(0).setOpen(true);
        return dtos;
    }

    @Override
    public AjaxResponse updateGroup(ArrayList<ExSysGroup> exSysGroups) {
        int count = groupMapper.updateGroup(exSysGroups);
        if (count > 0){
            return AjaxResponse.success("数据更新成功");
        }
        return AjaxResponse.success("数据更新失败");
    }

    @Override
    public String insertGroup(ExSysGroup exSysGroup) {
        String msg = "数据插入失败";
        int count = groupMapper.insertGroup(exSysGroup);
        if (count > 0){
            msg = "数据插入成功";
        }
        return msg;
    }

    @Override
    public PageAjaxResponse findResourceByPageCondition(PageParameter<ExSysResource> pageParameter) {
        List<ExSysResource> list = resourceMapper.findResourceByCondition(pageParameter);
        int count = resourceMapper.findCoyntByCondition(pageParameter);
        return PageAjaxResponse.success(list,count);
    }

    @Override
    public String saveResource(ExSysResource exSysResource) {
        String msg = "数据更新失败";
        int count = resourceMapper.insertResource(exSysResource);
        if (!LongUtils.longIsNull(exSysResource.getId())){
            msg = "数据更新成功";
        }
        return msg;
    }

    @Override
    public String updateResource(ArrayList<ExSysResource> exSysResources) {
        String msg = "更新失败";
        int count = resourceMapper.updateResourceByLit(exSysResources);
        if (count > 0){
            msg = "更新成功";
        }
        return msg;
    }

    @Override
    public List<DictionaryDto> findDictionaryByCondition(DictionaryDto dictionaryDto) {
        List<DictionaryDto> list = dictionaryMapper.findDictionaryByCondition(dictionaryDto);
        return list;
    }

    @Override
    @Transactional
    public AjaxResponse updateDictionary(ArrayList<DictionaryDto> dictionaryDtos) {
        AjaxResponse ajaxResponse = AjaxResponse.success("数据更新成功");
        List<ExSysDictionary> mainList = new ArrayList<ExSysDictionary>();
        dictionaryDtos.stream().forEach(item->{
            ExSysDictionary dictionary = new ExSysDictionary();
            BeanUtils.copyProperties(item,dictionary);
            dictionary.setDicUpdateTime(new Date());
            mainList.add(dictionary);

        });
        if (mainList.size() > 0){
            int count = dictionaryMapper.updateDictonary(mainList);
            if (count != mainList.size()){
                ajaxResponse.setMsg("数据更新失败");
                return ajaxResponse;
            }
            /**
             * 1、删除多个（）删除
             * 2、单个删除
             * 3、更新
             */
        }
        //不需要删除子
        if (dictionaryDtos.size() > 1 || dictionaryDtos.get(0) == null || CollectionUtils.isNull(dictionaryDtos.get(0).getChilds())){
            return ajaxResponse;
        }
        List<DictionaryDto> childList = new ArrayList<DictionaryDto>();
        dictionaryDtos.stream().forEach(item->{
            int count = dictionaryMapper.delDictonary(item);
            if (CollectionUtils.isNotNull(item.getChilds())){
                childList.addAll(item.getChilds());
            }
        });
        List<ExSysDictionary> list = new ArrayList<ExSysDictionary>();
        childList.stream().forEach(item->{
            ExSysDictionary dictionary = new ExSysDictionary();
            BeanUtils.copyProperties(item,dictionary);
            dictionary.setDicCreateTime(new Date());
            dictionary.setDicParentid(dictionaryDtos.get(0).getDicId());
            list.add(dictionary);
        });
        if (list.size() > 0){
            int count = dictionaryMapper.insertDictonary(list);
            if (count != list.size()){
                ajaxResponse.setMsg("数据更新失败");
                return ajaxResponse;
            }
        }
        return ajaxResponse;
    }

    @Override
    @Transactional
    public String insertDictionary(DictionaryDto dictionaryDto) {
        String msg = "数据更新成功";
        ExSysDictionary exSysDictionary = new ExSysDictionary();
        BeanUtils.copyProperties(dictionaryDto,exSysDictionary);
        List<ExSysDictionary> list = new ArrayList<ExSysDictionary>(1);
        exSysDictionary.setDicCreateTime(new Date());
        list.add(exSysDictionary);
        int count = dictionaryMapper.insertDictonary(list);
        if (count != list.size()){
            return "数据更新失败";
        }
        List<DictionaryDto> childs = dictionaryDto.getChilds();
        List<ExSysDictionary> childList = new ArrayList<ExSysDictionary>();
        childs.stream().forEach(item->{
            ExSysDictionary dictionary = new ExSysDictionary();
            BeanUtils.copyProperties(item,dictionary);
            dictionary.setDicParentid(list.get(0).getDicId());
            dictionary.setDicCreateTime(new Date());
            childList.add(dictionary);
        });
        if (childList.size() > 0){
            int childCount = dictionaryMapper.insertDictonary(childList);
            if (childCount != childList.size()){
                msg = "数据更新失败";
            }
        }
        return msg;
    }

    @Override
    public List<DictionaryDto> getChilds(DictionaryDto dictionaryDto) {
        List<DictionaryDto> dictionaryByCondition = dictionaryMapper.findDictionaryByCondition(dictionaryDto);
        return dictionaryByCondition;
    }

    @Override
    public void reloadDictionary() {
        DictionaryDto dictionaryDto = new DictionaryDto();
        dictionaryDto.setDicForbid(1L);
        List<DictionaryDto> dictionaryByCondition = dictionaryMapper.findDictionaryByCondition(dictionaryDto);
        DictionaryCache.initCache(dictionaryByCondition);
    }

    /**
     * 获取
     * @return
     */
    @Override
    public Map<String, List<DictionaryDto>> getLaytpl() {
        Map<String, List<DictionaryDto>> allDictionary = DictionaryCache.getAllDictionary();
        Map<String, List<DictionaryDto>> dictionary = new HashMap<>();
        for (String s : allDictionary.keySet()) {
            String key = "";
            List<DictionaryDto> valuList = allDictionary.get(s);
            DictionaryDto dto = valuList.stream().filter(dictionaryDto -> StringUtils.isNull(dictionaryDto.getDicKey())).findAny().orElse(null);
            if (dto == null){
                continue;
            }
            if ((dto.getDicIsLaytpl() == null ? 0L : dto.getDicIsLaytpl()) == 1L){
                key = StringUtils.isNull(dto.getDicLaytplId()) ? dto.getDicCode() : dto.getDicLaytplId();
                List<DictionaryDto> collect = valuList.stream().filter(dictionaryDto -> !StringUtils.isNull(dictionaryDto.getDicKey())).collect(Collectors.toList());
                dictionary.put(key,collect);
            }
        }
        return dictionary;
    }
}
