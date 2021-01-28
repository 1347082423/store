package com.ex.store.sys.service.impl;


import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysResource;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.pojo.ExSysUser;
import com.ex.store.core.util.TreeUtils;
import com.ex.store.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Describe
 * @Author wex
 * @Date 2020-9-2 9:33
 * @Version
 **/
@Service
public class UserServerImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Cacheable(key = "#loginName",value = "ExSysUser")
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        ExSysUser exSysUser = userMapper.loadUserByName(loginName);
        List<ExSysRole> rolePos = userMapper.loadRoleByUserId(exSysUser.getId());
        List<ExSysResource> exSysResources = new ArrayList<ExSysResource>();
        List<ExSysMenu> exSysMenuList = new ArrayList<ExSysMenu>();
        for (ExSysRole rolePo : rolePos) {
//            exSysResources.addAll(userMapper.loadResourceByRoleId(rolePo.getId()));
            if (rolePo.getType() == 2){
                exSysResources.addAll(userMapper.loadResourceByRoleId(rolePo.getId()));
            }else if (rolePo.getType() == 1){
                exSysMenuList.addAll(userMapper.loadMenuByRoleId(rolePo.getId()));
            }
        }
        List<SimpleGrantedAuthority> authorities = rolePos.stream()
                .map(rolePo -> new SimpleGrantedAuthority("ROLE_" + rolePo.getName())).collect(Collectors.toList());
        authorities.addAll(exSysResources.stream().map(resources -> new SimpleGrantedAuthority(resources.getUrl())).collect(Collectors.toList()));
        exSysUser.setAuthorities(authorities);
        List<MenuDto> menus = new ArrayList<MenuDto>();
        exSysMenuList.stream().forEach(exSysMenu -> {
            menus.add(new MenuDto(exSysMenu.getTitle(), exSysMenu.getName(), exSysMenu.getIcon(), exSysMenu.getUrl(), exSysMenu.getId(), exSysMenu.getPid()));
        });
        exSysUser.setMenus(TreeUtils.coverTree(menus));
//        exSysUser.setMenus(null);
        return exSysUser;
    }
}
