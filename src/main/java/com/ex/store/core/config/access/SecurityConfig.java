package com.ex.store.core.config.access;


import com.ex.store.sys.service.impl.UserServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Describe
 * @Author wex
 * @Date 2020/8/22 21:32
 * @Version
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServerImpl userServer;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServer).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**");
        web.ignoring().mvcMatchers("/js/**");
        web.ignoring().mvcMatchers("/lib/**");
        web.ignoring().mvcMatchers("/image/**");
        web.ignoring().mvcMatchers("/json/**");
        web.ignoring().mvcMatchers("/extend/**");
        web.ignoring().mvcMatchers("/favicon.ico");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .csrf().disable();//
        http.cors().disable();
        http.formLogin() //表单登陆 1
                .loginPage("/hello") //指定登陆页面
                .successForwardUrl("/index2")
                .failureForwardUrl("/failure")
                .and()
                .logout().logoutUrl("/delete")
                .and()
                .authorizeRequests() //开启登录校验
//                .antMatchers("/hello","/failure","/favicon.ico").permitAll()//hello路径不需要校验
                .anyRequest().access("@securityAuthorityAccess.hasPermit(request,authentication)");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
