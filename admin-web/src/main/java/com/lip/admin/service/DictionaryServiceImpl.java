/**
 * 
 */
package com.lip.admin.service;

import java.util.List;

import javax.annotation.Resource;

import com.lip.admin.dao.DictionaryDao;
import com.lip.admin.model.DictionaryItem;
import org.springframework.stereotype.Service;

/**
 * @author lip
 * @date 2015年12月30日
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
    
    @Resource
    private DictionaryDao dictionaryDao;
    

    /**
     * {@inheritDoc}
     * @date 2015年12月31日
     * @param typeCode
     * @return
     */
    @Override
    public List<DictionaryItem> selectByType(String typeCode) {
        return dictionaryDao.selectByType(typeCode);
    }

}
