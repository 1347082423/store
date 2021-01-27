package com.ex.store.sys.mapper;

import com.ex.store.core.dto.RoleAndResource;
import com.ex.store.core.pojo.ExSysRole;
import com.ex.store.core.pojo.ExSysRoleResource;
import com.ex.store.core.pojo.ExSysUserRole;
import com.ex.store.core.vo.PageParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author wex
 * @Date 2021-1-11 15:35
 * @Desc
 **/
@Mapper
public interface PermissionsMapper {
    List<ExSysRole> findPermissionsListByPage(PageParameter<ExSysRole> pageParameter);

    int findPermissionsCount(PageParameter<ExSysRole> pageParameter);

    List<Map<String, Object>> getPermissions(long id);

    int saveRoles(List<RoleAndResource> roleAndResources);

    int insertRole(RoleAndResource roleAndResource);

    int updateRoleResource(List<ExSysRoleResource> exSysRoleResources);

    List<ExSysRoleResource> findByRoleid(ExSysRoleResource exSysRoleResource);

    int saveRoleResource(List<ExSysRoleResource> exSysRoleResource);

    int deletRoleResource(ExSysRoleResource exSysRoleResource);

    int updateUserAndRoleByUserId(List<ExSysUserRole> exSysUserRoles);

    int deletRoleUser(ExSysUserRole exSysUserRole);

    List<Map<String, Object>> getResource(Long id);
}
