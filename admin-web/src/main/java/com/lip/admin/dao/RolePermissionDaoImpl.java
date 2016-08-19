/**
 * 
 */
package com.lip.admin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.RolePermissionItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.lip.core.utils.PKGenarator;
import com.google.common.collect.Maps;

/**
 * @author lip
 * @date 2015年12月30日
 */
@Repository
public class RolePermissionDaoImpl implements RolePermissionDao {
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private static String DELETE_BY_ROLE_ID = "delete from role_permission where role_id = :roleId";
    private static String INSERT_ITEMS_BATCH = "insert into role_permission (id,role_id,permission_id)  values(:id,:roleId,:permissionId)";
    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    public int savePermission4Role(String roleId, String[] permissionIds) {
        List<RolePermissionItem> list = new ArrayList<RolePermissionItem>();
        for (int i = 0 ,length = permissionIds.length; i < length; i++) {
            RolePermissionItem RolePermissionItem= new RolePermissionItem();
            RolePermissionItem.setId(PKGenarator.getId());
            RolePermissionItem.setRoleId(roleId);
            RolePermissionItem.setPermissionId(permissionIds[i]);
            list.add(RolePermissionItem);
        }
        ////批量更新操作
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(list.toArray());
        return jdbcTemplate.batchUpdate(INSERT_ITEMS_BATCH, params).length;
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param roleId
     * @return
     */
    @Override
    public int removePermission4Role(String roleId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("roleId", roleId);
        return jdbcTemplate.update(DELETE_BY_ROLE_ID, paramMap);
    }

}
