package com.ex.store.sys.controller;

import com.ex.store.core.dto.GroupTreeDto;
import com.ex.store.core.pojo.ExSysGroup;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wex
 * @Date 2021-1-20 10:54
 * @Desc
 **/

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private SysService sysService;

    @PostMapping("index")
    public AjaxResponse index(ExSysGroup exSysGroup){
        List<ExSysGroup> exSysGroupList = sysService.getAllGroup(exSysGroup);
        return AjaxResponse.success(exSysGroupList);
    }
}
