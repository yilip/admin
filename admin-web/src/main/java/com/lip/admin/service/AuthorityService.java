/**
 * 
 */
package com.lip.admin.service;

import java.util.List;
import java.util.Map;

import com.lip.admin.model.PermissionItem;
import com.lip.admin.model.RoleItem;

/**
 * @author lip
 * @date 2015年12月29日
 */
public interface AuthorityService {
    /**
     * 根据pid获取permission
     */
    List<PermissionItem> getPermissionListByPid(String pid);
    /**
     * 修改权限
     */
    int modifyPermission(PermissionItem permissionModel);

    /**
     * 添加权限
     */
    int savePermission(PermissionItem permissionModel);

    /**
     * 排序权限列表
     */
    int modifyOrderByPermission(String targetId, String sourceId, String point);

    /**
     * 删除权限
     */
    int removePermission(String id);
    
    
    /**获取角色列表
     */
    List<RoleItem> getRoleList(RoleItem roleItem);
    /**
     * 添加角色
     */
    int saveRole(RoleItem roleItem);
    /**
     * 修改角色
     */
    int modifyRole(RoleItem roleItem);
    /**
     * 删除角色
     */
    int removeRole(String id);
    /**
     * 保存角色权限
     */
    int savePermission4Role(String roleId, String permissionId, String permissionChecked);
    /**
     * 删除角色权限
     */
    int removePermission4Role(String roleId);
    /**
     * 获取角色权限
     */
    List<PermissionItem> getPermission4Role(String roleId);
    /**
     * 查询所有权限
     */
    List<PermissionItem> getPermissionList(PermissionItem permissionItem);
    /**
     * 给用户添加角色
     */
    int saveRole4User(String userId, String roleId);
    /**
     * 删除用户的角色
     */
    int removeRole4User(String id);
    /**
     * 用户下的角色
     * @return
     */
    List<RoleItem> getRole4User(String userId);
    /**
     * 角色下的用户
     */
    List<Map<String, Object>> getUserByRole(String roleCode);
    
}
