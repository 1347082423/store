package com.ex.store.sys.controller;

import com.ex.store.core.dto.UserDto;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wex
 * @Date 2021-1-15 15:23
 * @Desc
 **/

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysService sysService;

    @PostMapping("index")
    public PageAjaxResponse index(PageParameter<UserDto> pageParameter,UserDto userDto){
        pageParameter.setParameter(userDto);
        PageAjaxResponse pageAjaxResponse = sysService.findUserByPage(pageParameter);
        return pageAjaxResponse;
    }
}
