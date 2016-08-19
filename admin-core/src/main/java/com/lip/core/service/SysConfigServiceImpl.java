package com.lip.core.service;

import com.lip.core.dao.SysConfigDataDao;
import com.lip.core.model.SysConfigItem;
import com.lip.core.utils.PKGenarator;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    private final Map<String, SysConfigItem> cache = Maps.newHashMap();

    @Inject
    private SysConfigDataDao sysConfigDataDao;

    @PostConstruct
    void init(){
        refresh();
    }

    @Override
    public SysConfigItem fetchSysConfig(String displayName) {
        return cache.get(displayName);
    }

    private synchronized void refresh() {
        cache.clear();
        for(SysConfigItem item : sysConfigDataDao.fetchAllConfig()) {
            cache.put(item.getDisplayName(), item);
        }
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param id
     * @return
     */
    @Override
    public int removeSysConfig(String id) {
        return sysConfigDataDao.removeSysConfig(id);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public int modifySysConfig(SysConfigItem sysConfigItem) {
        return sysConfigDataDao.modifySysConfig(sysConfigItem);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public int saveSysConfig(SysConfigItem sysConfigItem) {
        sysConfigItem.setId(PKGenarator.getId());
        return sysConfigDataDao.saveSysConfig(sysConfigItem);
    }

    /**
     * {@inheritDoc}
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @Override
    public List<SysConfigItem> getSysConfigList(SysConfigItem sysConfigItem) {
        return sysConfigDataDao.getSysConfigList(sysConfigItem);
    }

}
