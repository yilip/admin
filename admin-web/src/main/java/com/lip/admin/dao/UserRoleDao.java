/**
 * 
 */
package com.lip.admin.dao;

import java.util.List;
import java.util.Map;

/**
 * @author lip
 * @date 2015年12月30日
 */
public interface UserRoleDao {
    int saveRole4User(String userId, String roleId);

    /**
     * Description: 根据用户名删除该用户下的所有角色
     */
    int removeRole4UserByuserId(String userId);
    
    /**
     * Description: 根据roleId查询用户列表
     */
    public List<Map<String,Object>> getUserByRoleId(String roleId);
   
}
