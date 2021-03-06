package com.ex.store.sys.controller;

import com.ex.store.core.dto.MenuDto;
import com.ex.store.core.pojo.ExSysUser;
import com.ex.store.core.util.CollectionUtils;
import com.ex.store.core.vo.AjaxResponse;
import com.ex.store.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Describe
 * @Author wex
 * @Date 2020/8/22 18:39
 * @Version
 *
 * @Cacheable - 表明对应方法的返回结果可以被缓存，首次调用后，下次就从缓存中读取结果，方法不会再被执行了。
 * @CachePut - 更新缓存，方法每次都会执行
 * @CacheEvict - 清除缓存，方法每次都会执行
 **/
@Controller
public class PortalController {

    @Autowired
    private SysService sysService;


    @GetMapping("/hello")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @GetMapping("/a")
    public String a(HttpServletRequest request, HttpServletResponse response) {
        return "a";
    }

    @RequestMapping("/index2")
    public String index2() {
        return "index2";
    }

    @GetMapping("/loginFail")
    public String loginFail() {
        return "404";
    }
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/delete")
//    @ResponseBody
//    @CacheEvict(key = "#loginName")
    public String delete(){
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return "login";
    }

    @ResponseBody
    @RequestMapping("/url1")
    public String url1() {
        ExSysUser principal = (ExSysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Thread.sleep(6000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Admin";
    }

    @ResponseBody
    @RequestMapping("/url2")
    public String url2() {
        return "User";
    }

    @ResponseBody
    @RequestMapping("/url3")
    public String url3() {
        ExSysUser principal = (ExSysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "Admin";
    }

    @RequestMapping("/failure")
    @ResponseBody
    public String loginFailure() {

        return "登录失败了，老哥" + HttpStatus.UNAUTHORIZED.value();
    }

    /**
     * 登录成功后拿到个人信息.
     *
     * @return the rest
     */
    @RequestMapping("/success")
//    @ResponseBody
    public String loginSuccess() {
        // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        ExSysUser principal = (ExSysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getName();
//        return "登录成功" + username;
        return "aaaa";
    }

    @RequestMapping("/obtainmenu")
    @ResponseBody
    public AjaxResponse obtainMenu() {
        ExSysUser principal = (ExSysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MenuDto> list = null;
        if (CollectionUtils.isNull(principal.getMenus())){
            list= sysService.obtainMenu();
        }else{
            list = principal.getMenus();
        }
        return AjaxResponse.success(list);
    }

}
