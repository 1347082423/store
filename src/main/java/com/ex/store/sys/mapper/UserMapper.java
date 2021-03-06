package com.ex.store.sys.mapper;

import com.ex.store.core.dto.UserDto;
import com.ex.store.core.pojo.*;
import com.ex.store.core.vo.PageParameter;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Describe
 * @Author wex
 * @Date 2020-9-2 10:02
 * @Version
 **/
@Mapper
public interface UserMapper {
    @Select("select exuser.name, exuser.loginname,exuser.address,exuser.email,exuser.id,exuser.`password`,exuser.phone,true as enabled " +
            "from ex_sys_user exuser where exuser.loginname = #{name}")
    @Results(id = "userInf",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "name",column = "name"),
            @Result(property = "username",column = "loginname"),
            @Result(property = "address",column = "address"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "enable",column = "enable")
    })
    ExSysUser loadUserByName(@Param("name") String name);

    @Select("select role.id,role.`name`,role.rolename,role.`desc`,role.`type` from ex_sys_role role,ex_sys_user_role u_r " +
            "where role.id = u_r.roleid and u_r.userid = #{id} and role.isForbid=1")
    List<ExSysRole> loadRoleByUserId(@Param("id") Long id);

    @Select("select resource.id,resource.`name`,resource.category,resource.url,resource.`desc` from ex_sys_resource resource,ex_sys_role_resource r_r" +
            " where r_r.resourceid = resource.id and r_r.roleid = #{id}")
    List<ExSysResource> loadResourceByRoleId(@Param("id") Long id);

    @Select("select resource.id,resource.`name`,resource.category,resource.url,resource.`desc`,resource.`pid`,resource.`title`,`icon` icon  from ex_sys_menu resource,ex_sys_role_resource r_r" +
            " where r_r.resourceid = resource.id and r_r.roleid = #{id} order by sort asc")
    List<ExSysMenu> loadMenuByRoleId(@Param("id") Long id);

    List<UserDto> findUserListByPage(PageParameter pageParameter);

    int findUserPageCount(PageParameter pageParameter);

    List<Map<String, Object>> loadGroupByUserId(Long id);

    int updateUserByList(List<UserDto> userDtos);

    int insertUser(ExSysUser exSysUser);

}
