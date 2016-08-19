/**
 * 
 */
package com.lip.admin.dao;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lip
 * @date 2015年12月30日
 */
public interface RolePermissionDao {
    /**
     * 批量插入角色的权限
     * @date 2015年12月30日
     * @param roleId
     * @param permissionId
     * @return
     */
    int savePermission4Role(String roleId, String[] permissionId);
    int removePermission4Role(String roleId);
   
}
