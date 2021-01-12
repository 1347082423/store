package com.ex.store.sys.controller;

import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.pojo.ExSysMenu;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
