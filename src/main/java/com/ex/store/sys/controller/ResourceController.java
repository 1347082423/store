package com.ex.store.sys.controller;

import com.ex.store.core.pojo.ExSysResource;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wex
 * @Date 2021-1-25 13:10
 * @Desc
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private SysService sysService;

    @PostMapping("index")
    public PageAjaxResponse index(ExSysResource exSysResource, PageParameter<ExSysResource> pageParameter){
        pageParameter.setParameter(exSysResource);
        PageAjaxResponse ajaxResponse = sysService.findResourceByPageCondition(pageParameter);
        return ajaxResponse;
    }
}
