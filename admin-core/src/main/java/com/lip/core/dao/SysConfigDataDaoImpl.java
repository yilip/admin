package com.lip.core.dao;

import com.lip.core.model.SysConfigItem;
import com.google.common.collect.Maps;
import org.javasimon.aop.Monitored;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class SysConfigDataDaoImpl implements SysConfigDataDao {

    private static String SELECT_SYS_CONFIG = "select * from sys_config";
    private static String DELETE_BY_ID = "delete from sys_config where id=:id";
    private static String INSERT_A_ITEM = "insert into sys_config(id,code,description,display_name) values(:id,:code,:description,:displayName)";
    private static String UPDATE_BY_ITEM = "update sys_config set "
            + " code=coalesce(:code,code) "
            + " ,description=coalesce(:description,description) "
            + " ,display_name=coalesce(:displayName,display_name) "
            + " where id=:id";

    @Inject
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Monitored
    @Override
    public List<SysConfigItem> fetchAllConfig() {
        Map<String, Object> paramMap = Maps.newHashMap();
        return jdbcTemplate.query(SELECT_SYS_CONFIG, paramMap, productRowMapper);
    }

    private final RowMapper<SysConfigItem> productRowMapper = new RowMapper<SysConfigItem>() {

        @Override
        public SysConfigItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            SysConfigItem item = new SysConfigItem();
            item.setId(rs.getString("id"));
            item.setCode(rs.getString("code"));
            item.setDescription(rs.getString("description"));
            item.setDisplayName(rs.getString("display_name"));
            return item;
        }
    };

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param id
     * @return
     */
    @Override
    public int removeSysConfig(String id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", id);
        return jdbcTemplate.update(DELETE_BY_ID, params);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public int saveSysConfig(SysConfigItem sysConfigItem) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysConfigItem);
        return jdbcTemplate.update(INSERT_A_ITEM, paramSource);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public List<SysConfigItem> getSysConfigList(SysConfigItem item) {
        Map<String,Object> paramMap = Maps.newHashMap();
        String sql = "select * from sys_config ";
        StringBuilder condition = new StringBuilder();
        if(item.getId()!=null){
            condition.append("and id = :id ");
            paramMap.put("id", item.getId());
        }
        if(item.getCode()!=null){
            condition.append("and code like :code ");
            paramMap.put("code", "%"+item.getCode()+"%");
        }
        if(item.getDisplayName()!=null){
            condition.append("and display_name like :display_name ");
            paramMap.put("display_name", "%"+item.getDisplayName()+"%");
        }
        if(condition.toString().startsWith("and")){
            String finaleStr = condition.toString().substring(3, condition.length());
            sql = sql + " where " + finaleStr;
        }
        
        return jdbcTemplate.query(sql, paramMap, productRowMapper);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public int modifySysConfig(SysConfigItem sysConfigItem) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(sysConfigItem);
        return jdbcTemplate.update(UPDATE_BY_ITEM, paramSource);
    }
}
