/**
 * 
 */
package com.lip.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.PermissionItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * @author lip
 * @date 2015年12月29日
 */
@Repository
public class PermissionDaoImpl implements PermissionDao{
    
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private static String SELECT_BY_PID = "select * from permission  where pid=:pid order by seq_num";
    
    private static String SELECT_BY_ID = "select * from permission  where id=:id";
    
    private static String UPDATE_BY_ID = " update permission set code=:code, label=:label, sys_type=:sysType, permission_type=:permissionType, url=:url, pid=:pid, status=:status, description=:description, expanded=:expanded, language=:language, target=:target, icon=:icon, seq_num=:seqNum"
            + " where id=:id";
    
    private static String SELECT_MAX_SEQNUM_BY_PID = "SELECT IFNULL(MAX(seq_num),(SELECT seq_num FROM permission WHERE id =:pid)) FROM permission where pid=:pid";
    
    private static String INSERT_A_ITEM = "insert into permission(id, code, label,sys_type, permission_type, url,pid, status, description,expanded, language, target,icon, seq_num)"
            + " values(:id,:code, :label, :sysType, :permissionType, :url, :pid, :status, :description, :expanded, :language, :target, :icon, :seqNum)";
    private static String DELETE_BATCH_BY_ID = "delete from permission where id in";
    
    private static String SELECT_PERMISSION_FOR_ROLE = "SELECT t1.* FROM permission t1,role_permission t2 WHERE t1.id = t2.permission_id AND t2.role_id = :roleId";
    private static String SELECT_ALL = "select * from permission";
    
    /**{@inheritDoc}
     * @date 2015年12月29日
     * @param item
     * @return
     */
    @Override
    public List<PermissionItem> getPermissionList(PermissionItem item) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(item);
        return jdbcTemplate.query(SELECT_BY_PID, parameterSource, rowMapper);
    }

    
    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param targetId
     * @return
     */
    @Override
    public PermissionItem getPermissionById(String targetId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("id", targetId);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, paramMap, rowMapper);
    }


    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param operate
     * @param sourceSeqNum
     * @param targetSeqNum
     */
    @Override
    public void modifyOrderByPermission(String operate, Integer startSeqNum, Integer targetSeqNum) {
        Map<String,Object> paramMap = Maps.newHashMap();
        
        String sql = "update permission";
        if("top".equals(operate)){
            sql += " set seq_num = seq_num+1 ";
        }
        if("bottom".equals(operate)){
            sql += " set seq_num = seq_num-1 ";
        }
        if(null!=startSeqNum){///起始下标存在
            if(null!=targetSeqNum){//结束下标存在
                sql += " where seq_num >= :startSeqNum and seq_num <= :startSeqNum";
                paramMap.put("targetSeqNum", targetSeqNum);
                paramMap.put("startSeqNum", startSeqNum);
            }else{
                sql += " where seq_num >= :startSeqNum";
                paramMap.put("startSeqNum", startSeqNum);
            }
        }else{///起始下标不存在
            if(null!=targetSeqNum){
                sql += " where seq_num <= :targetSeqNum";
                paramMap.put("targetSeqNum", targetSeqNum);
            }else{
                return ;///不做任何操作
            }
        }
        
        jdbcTemplate.update(sql, paramMap);
    }



    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param sourcePermission
     */
    @Override
    public int modifyPermission(PermissionItem item) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(item);
        return jdbcTemplate.update(UPDATE_BY_ID, parameterSource);
    }
    
    
    
    private final RowMapper<PermissionItem> rowMapper = new RowMapper<PermissionItem>() {
        @Override
        public PermissionItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            PermissionItem item  = new PermissionItem();
            item.setCode(rs.getString("code"));
            item.setDescription(rs.getString("description"));
            item.setExpanded(rs.getString("expanded"));
            item.setIcon(rs.getString("icon"));
            item.setId(rs.getString("id"));
            item.setLabel(rs.getString("label"));
            item.setLanguage(rs.getString("language"));
            item.setPermissionType(rs.getString("permission_type"));
            item.setPid(rs.getString("pid"));
            item.setSeqNum(rs.getInt("seq_num"));
            item.setStatus(rs.getString("status"));
            item.setSysType(rs.getString("sys_type"));
            item.setTarget(rs.getString("target"));
            item.setUrl(rs.getString("url"));
            
            return item;
        }
    };

    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param pid
     * @return
     */
    @Override
    public int getMaxSeqNumByPid(String pid) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("pid", pid);
        return jdbcTemplate.queryForInt(SELECT_MAX_SEQNUM_BY_PID, paramMap);
    }


    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param permissionItem
     * @return
     */
    @Override
    public int savePermission(PermissionItem item) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(item);
        return jdbcTemplate.update(INSERT_A_ITEM, parameterSource);
    }


    /**
     * {@inheritDoc}
     * @date 2015年12月29日
     * @param id
     * @return
     */
    @Override
    public int removePermissionBatch(String[] ids) {
        String sql = DELETE_BATCH_BY_ID;
        StringBuilder str = new StringBuilder("(");
        for(String id:ids){
            str.append("'").append(id).append("',");
        }
        if(str.toString().endsWith(",")){
            sql += str.substring(0, str.length()-1);///去掉结尾的","
            sql += ")";
        }
        
        Map<String,Object> paramMap = Maps.newHashMap();
        return jdbcTemplate.update(sql, paramMap);
    }


    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @param roleId
     * @return
     */
    @Override
    public List<PermissionItem> getPermission4Role(String roleId) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("roleId", roleId);
        return jdbcTemplate.query(SELECT_PERMISSION_FOR_ROLE, paramMap,rowMapper);
    }


    /**
     * {@inheritDoc}
     * @date 2016年1月7日
     * @return
     */
    @Override
    public List<PermissionItem> getAllPermission() {
        Map<String,Object> paramMap = Maps.newHashMap();
        return jdbcTemplate.query(SELECT_ALL, paramMap,rowMapper);
    }
    
    

}
