/**
 * 
 */
package com.lip.admin.service;

import java.util.List;

import com.lip.admin.model.DictionaryItem;

/**
 * @author lip
 * @date 2015年12月30日
 */
public interface DictionaryService {
    
    /**
     * @date 2015年12月31日
     * @param typeCode
     * @return
     */
    public List<DictionaryItem> selectByType(String typeCode);
}
