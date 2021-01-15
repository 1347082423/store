package com.ex.store.sys.controller;

import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-11 15:25
 * @Desc
 **/

@RestController
@RequestMapping("permissions")
public class PermissionsController {

    @Autowired
    private SysService sysService;

    @PostMapping("index")
    public PageAjaxResponse index(PageParameter<ExSysRole> pageParameter , ExSysRole exSysMenu){
        pageParameter.setParameter(exSysMenu);
        PageAjaxResponse ajaxResponse = sysService.getAllPermissions(pageParameter);
        return ajaxResponse;
    }

    @PostMapping("saveRole")
    public AjaxResponse saveRole(RoleAndResource exSysMenu){
        List<RoleAndResource> list = new ArrayList<RoleAndResource>();
        list.add(exSysMenu);
        AjaxResponse ajaxResponse = sysService.saveRoles(list);
        return ajaxResponse;
    }

    /**
     * 单行删除按钮批量删除
     * @param exSysMenus
     * @return
     */
    @PostMapping("saveRoles")
    public AjaxResponse saveRoles(@RequestBody ArrayList<RoleAndResource> exSysMenus){
        AjaxResponse ajaxResponse = sysService.saveRoles(exSysMenus);
        return ajaxResponse;
    }

    /**
     * 单行编辑按钮与插入
     * @param exSysMenus
     * @return
     */
    @PostMapping("insertRole")
    public AjaxResponse insertRole(RoleAndResource exSysMenus){
        String msg = "";
        try {
            msg = sysService.insertRole(exSysMenus);
        }catch (BusinessException e){
            msg = e.getMessage();
        }
        return AjaxResponse.success(msg);
    }
}
