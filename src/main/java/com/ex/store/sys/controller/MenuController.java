package com.ex.store.sys.controller;

import com.ex.store.core.pojo.ExSysMenu;
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
 * @Date 2021-1-5 15:30
 * @Desc
 **/
@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private SysService sysService;

    @ResponseBody
    @RequestMapping("index")
    public PageAjaxResponse index(HttpServletRequest request, PageParameter<ExSysMenu> pageParameter , ExSysMenu exSysMenu){
        pageParameter.setParameter(exSysMenu);
        PageAjaxResponse ajaxResponse = sysService.getAllMenu(pageParameter);
        return ajaxResponse;
    }

    @ResponseBody
    @RequestMapping("getAllMenu")
    public AjaxResponse getAllMenu(){
        AjaxResponse ajaxResponse = sysService.getAllMenu();
        return ajaxResponse;
    }

    @ResponseBody
    @PostMapping("saveMenu")
    public AjaxResponse saveMenu(ExSysMenu exSysMenu){
        String msg = sysService.saveMenu(exSysMenu);
        return AjaxResponse.success(msg);
    }


    @ResponseBody
    @PostMapping("isForbid")
    public AjaxResponse isForbid(@RequestBody ArrayList<ExSysMenu> exSysMenus){
        String msg = sysService.isForbid(exSysMenus);
        return AjaxResponse.success(msg);
    }
}
