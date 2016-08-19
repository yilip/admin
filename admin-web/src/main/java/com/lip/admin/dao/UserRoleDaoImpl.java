/**
 * 
 */
package com.lip.admin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.UserRoleItem;
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
public class UserRoleDaoImpl implements UserRoleDao {

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private static String INSERT_ITEMS_BATCH = "insert into user_role (id,user_id,role_id)  values(:id,:userId,:roleId)";
    private static String DELETE_BY_USER_ID = "delete from user_role  where user_id =:userId";
    private static String SELECT_USER_BY_ROLE_ID = "select a.admin_id as adminId, a.real_name as realName, a.employee_no as employeeNo, a.mobile "
            + "from user_role ur   left join admin a on a.id=ur.user_id  left join role r on r.id=ur.role_id where r.code =:roleCode";
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int saveRole4User(String userId, String roleId) {
        List<UserRoleItem> list = new ArrayList<UserRoleItem>();
        String roleIds[] = roleId.split(",");
        for (int i = 0 ,length = roleIds.length; i < length; i++) {
            UserRoleItem UserRoleItem= new UserRoleItem();
            UserRoleItem.setId(PKGenarator.getId());
            UserRoleItem.setUserId(userId);
            UserRoleItem.setRoleId(roleIds[i]);
            list.add(UserRoleItem);
        }
        ////批量更新操作
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(list.toArray());
        return jdbcTemplate.batchUpdate(INSERT_ITEMS_BATCH, params).length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int removeRole4UserByuserId(String userId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return jdbcTemplate.update(DELETE_BY_USER_ID, paramMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> getUserByRoleId(String roleId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("roleId", roleId);
        return jdbcTemplate.queryForList(SELECT_USER_BY_ROLE_ID, paramMap);
    }

}
