package com.ex.store.core.config.access;

import com.ex.store.core.pojo.ExSysUser;
import com.ex.store.core.util.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Describe
 * @Author wex
 * @Date 2020-9-7 9:48
 * @Version
 **/

@Component
public class SecurityAuthorityAccess {

    private List<String> skipUrls = new ArrayList<String>();


    public boolean hasPermit(HttpServletRequest request, Authentication authentication) {
        //定义不在权限范围中但是能够直接通过的路径，如果有返回false，无返回true
        skipUrls.add("/delete");
        skipUrls.add("/hello");
        skipUrls.add("/failure");
        skipUrls.add("/index");
        skipUrls.add("/obtainmenu");
        skipUrls.add("/favicon.ico");
        if (!CollectionUtils.isNull(skipUrls)) {
            for (String skipUrl : skipUrls) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(skipUrl);
                if (antPathRequestMatcher.matches(request)) {
                    return true;
                }
            }

        }
        Object principal = authentication.getPrincipal();
        ExSysUser exSysUser = null;
        if (principal instanceof ExSysUser)
            exSysUser = (ExSysUser) principal;
        if (exSysUser != null) {
            Collection<? extends GrantedAuthority> authorities = exSysUser.getAuthorities();
            return authorities.stream().anyMatch(authoritie -> new AntPathRequestMatcher(((GrantedAuthority) authoritie).getAuthority()).matches(request));
        }

        return false;
    }

}
