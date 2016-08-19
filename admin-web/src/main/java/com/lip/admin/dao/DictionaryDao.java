/**
 * 
 */
package com.lip.admin.dao;

import java.util.List;

import com.lip.admin.model.DictionaryItem;

/**
 * @author lip
 * @date 2015年12月30日
 */
public interface DictionaryDao {

    /**
     *  获取所有资源分类名称
     * @date 2015年12月30日
     * @return
     */
    List<DictionaryItem> getAllDicType();

    /**
     * @date 2015年12月31日
     * @param typeCode
     * @return
     */
    List<DictionaryItem> selectByType(String typeCode);
    
}
