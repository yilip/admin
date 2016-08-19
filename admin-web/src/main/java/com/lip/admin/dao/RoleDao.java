/**
 * 
 */
package com.lip.admin.dao;

import java.util.List;

import com.lip.admin.model.RoleItem;

/**
 * @author lip
 * @date 2015年12月30日
 */
public interface RoleDao {

    /**
     * 查询角色列表
     * @date 2015年12月30日
     * @param roleItem
     * @return
     */
    public List<RoleItem> getRoleList(RoleItem roleItem);

    public int saveRole(RoleItem roleItem) ;

    public List<RoleItem> getRole4User(String userId) ;

    public int modifyRole(RoleItem roleItem) ;

    public int removeRole(String id);

}
