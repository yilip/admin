/**
 * 
 */
package com.lip.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.RoleItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * 角色管理DAO
 * @author lip
 * @date 2015年12月30日
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    
    
    private static String INSERT_A_ITEM = "insert into role (id, code, name, pid, description) values(:id, :code, :name, :pid, :description)";
    
    private static String SELECT_ROLE4USER = "SELECT t1.* FROM role t1,user_role t2 where t1.id=t2.role_id and t2.user_id = :userId";
    private static String UPDATE_BY_PK = "update role set code = :code,name = :name,pid =:pid,description = :description where id = :id";
    private static String DELETE_BY_PK = "delete from role where id =:id";
    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param roleItem
     * @return
     */
    @Override
    public List<RoleItem> getRoleList(RoleItem roleItem) {
        Map<String,Object> paramMap = Maps.newHashMap();
        String sql = "select * from role ";
        StringBuilder condition = new StringBuilder();
        if(roleItem.getId()!=null){
            condition.append("and id = :id ");
            paramMap.put("id", roleItem.getId());
        }
        if(roleItem.getCode()!=null){
            condition.append("and code like :code ");
            paramMap.put("code", "%"+roleItem.getCode()+"%");
        }
        if(roleItem.getName()!=null){
            condition.append("and name like :name ");
            paramMap.put("name", "%"+roleItem.getName()+"%");
        }
        if(roleItem.getPid()!=null){
            condition.append("and pid = :pid ");
            paramMap.put("pid", roleItem.getPid());
        }
        if(roleItem.getDescription()!=null){
            condition.append("and description = :description ");
            paramMap.put("description", roleItem.getDescription());
        }
        if(condition.toString().startsWith("and")){
            String finaleStr = condition.toString().substring(3, condition.length());
            sql = sql + " where " + finaleStr;
        }
        
        return jdbcTemplate.query(sql, paramMap, rowMapper);
    }
   

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param RoleItem
     * @return
     */
    @Override
    public int saveRole(RoleItem roleItem) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(roleItem);
        return jdbcTemplate.update(INSERT_A_ITEM, paramSource);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param userId
     * @return
     */
    @Override
    public List<RoleItem> getRole4User(String userId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        return jdbcTemplate.query(SELECT_ROLE4USER, paramMap, rowMapper);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param RoleItem
     * @return
     */
    @Override
    public int modifyRole(RoleItem roleItem) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(roleItem);
        return jdbcTemplate.update(UPDATE_BY_PK, paramSource);
    }

    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param id
     * @return
     */
    @Override
    public int removeRole(String id) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("id", id);
        return jdbcTemplate.update(DELETE_BY_PK, paramMap);
    }
    
    
    
    private RowMapper<RoleItem> rowMapper = new RowMapper<RoleItem>() {
        @Override
        public RoleItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoleItem item = new RoleItem();
            item.setCode(rs.getString("code"));
            item.setDescription(rs.getString("description"));
            item.setId(rs.getString("id"));
            item.setName(rs.getString("name"));
            item.setPid(rs.getString("pid"));
            return item;
        }
    };

}
