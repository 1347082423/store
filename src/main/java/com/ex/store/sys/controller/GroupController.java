package com.ex.store.sys.controller;

import com.ex.store.core.dto.GroupTreeDto;
import com.ex.store.core.dto.TreeDto;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.pojo.ExSysGroup;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PostMapping("groupTree")
    public AjaxResponse getGroupByTree(ExSysGroup exSysGroup){
        List<TreeDto> exSysGroupList = sysService.getGroupByTree(exSysGroup);
        return AjaxResponse.success(exSysGroupList);
    }

    /**
     * 删除为逻辑删除
     * @param exSysGroups
     * @return
     */
    @PostMapping("updateGroup")
    public AjaxResponse updateGroup(@RequestBody ArrayList<ExSysGroup> exSysGroups){
        AjaxResponse ajaxResponse = sysService.updateGroup(exSysGroups);
        return ajaxResponse;
    }

    /**
     * 单行编辑按钮与单行添加与添加
     * @param
     * @return
     */
    @PostMapping("insertGroup")
    public AjaxResponse insertGroup(ExSysGroup exSysGroup){
        String msg = "";
        try {
            msg = sysService.insertGroup(exSysGroup);
        }catch (BusinessException e){
            msg = e.getMessage();
        }
        return AjaxResponse.success(msg);
    }
}
