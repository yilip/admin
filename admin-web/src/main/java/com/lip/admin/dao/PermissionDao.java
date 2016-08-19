/**
 * 
 */
package com.lip.admin.dao;

import java.util.List;

import com.lip.admin.model.PermissionItem;


/**
 * @author lip
 * @date 2015年12月29日
 */
public interface PermissionDao {
    /**
     * 根据pid获取permission
     * @date 2015年12月29日
     * @param item
     * @return
     */
    List<PermissionItem> getPermissionList(PermissionItem item);

    /**
     */
    PermissionItem getPermissionById(String targetId);

    /**
     */
    void modifyOrderByPermission(String operate, Integer sourceSeqNum, Integer targetSeqNum);

    /**
     */
    int modifyPermission(PermissionItem sourcePermission);

    /**
     */
    int getMaxSeqNumByPid(String pid);

    /**
     */
    int savePermission(PermissionItem permissionItem);

    /**
     * 根据id批量删除permission
     * @date 2015年12月29日
     * @param id
     * @return
     */
    int removePermissionBatch(String[] ids);

    List<PermissionItem> getPermission4Role(String roleId);
    
    List<PermissionItem> getAllPermission();
    
    
}
