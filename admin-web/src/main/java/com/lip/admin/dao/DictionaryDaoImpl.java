/**
 * 
 */
package com.lip.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.DictionaryItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

/**
 * @author lip
 * @date 2015年12月30日
 */
@Repository
public class DictionaryDaoImpl implements DictionaryDao {
    
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private static String SELECT_ALL_SOURCE_TYPE = "SELECT DISTINCT type_code,type_name FROM dictionary";
    
    private static String SELECT_BY_TYPE = "select * from dictionary where type_code = :typeCode order by seq_num";
    
    /**
     * {@inheritDoc}
     * @date 2015年12月30日
     * @return
     */
    @Override
    public List<DictionaryItem> getAllDicType() {
        Map<String,Object> paramMap = Maps.newHashMap();
        return jdbcTemplate.query(SELECT_ALL_SOURCE_TYPE, paramMap, rowMapper);
    }

    
    private RowMapper<DictionaryItem> rowMapper = new RowMapper<DictionaryItem>() {
        @Override
        public DictionaryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            DictionaryItem item = new DictionaryItem();
            item.setCode(rs.getString("code"));
            item.setDescription(rs.getString("description"));
            item.setDisplayName(rs.getString("display_name"));
            item.setId(rs.getString("id"));
            item.setPid(rs.getString("pid"));
            item.setSeqNum(rs.getInt("seq_num"));
            item.setTypeCode(rs.getString("type_code"));
            item.setTypeName(rs.getString("type_name"));
            return item;
        }
    };
    /**
     * {@inheritDoc}
     * @date 2015年12月31日
     * @param typeCode
     * @return
     */
    @Override
    public List<DictionaryItem> selectByType(String typeCode) {
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("typeCode", typeCode);
        return jdbcTemplate.query(SELECT_BY_TYPE, paramMap, rowMapper);
    }
    
    
    
    
}
