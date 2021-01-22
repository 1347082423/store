package com.ex.store.sys.controller;

import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.dto.UserDto;
import com.ex.store.core.exception.BusinessException;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.core.vo.PageAjaxResponse;
import com.ex.store.core.vo.PageParameter;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取用户首页信息
     * @param pageParameter
     * @param userDto
     * @return
     */
    @PostMapping("index")
    public PageAjaxResponse index(PageParameter<UserDto> pageParameter,UserDto userDto){
        pageParameter.setParameter(userDto);
        PageAjaxResponse pageAjaxResponse = sysService.findUserByPage(pageParameter);
        return pageAjaxResponse;
    }

    /**
     * 修改用户信息
     * 删除为逻辑删除
     * @param userDtos
     * @return
     */
    @PostMapping("delUser")
    public AjaxResponse delUser(@RequestBody ArrayList<UserDto> userDtos){
        AjaxResponse ajaxResponse = sysService.delUser(userDtos);
        return ajaxResponse;
    }

    /**
     * 单行编辑按钮与插入
     * @param
     * @return
     */
    @PostMapping("insertUser")
    public AjaxResponse insertUser(UserDto userDto){
        String msg = "";
        try {
            msg = sysService.insertUser(userDto);
        }catch (BusinessException e){
            msg = e.getMessage();
        }
        return AjaxResponse.success(msg);
    }

    @PostMapping("regist")
    public AjaxResponse regist(UserDto userDto){
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        String msg = "";
        try {
            msg = sysService.insertUser(userDto);
        }catch (BusinessException e){
            msg = e.getMessage();
        }
        return AjaxResponse.success(msg);
    }
}
