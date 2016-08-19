package com.lip.core.service;

import com.lip.core.dao.SysConfigDataDao;
import com.lip.core.model.SysConfigItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * 系统配置缓存
 * @author sunbo
 *
 */
@Component
public class SysConfigCache{
    private static final Logger logger = LoggerFactory.getLogger(SysConfigCache.class);
    
    private static HashMap<String,String> cacheMap;

    @Inject
    private SysConfigDataDao sysConfigDao;

    private static SysConfigCache sysCache;

    //FIXME remove me
    public static SysConfigCache getInstance() {
        return sysCache;
    }

    @PostConstruct
    public void init() {
        sysCache = this;
        sysCache.loadAll();
    }
    /**
     * 加载缓存
     */
    public  synchronized void loadAll() {
        logger.info("===========得到缓存开始===================");
        cacheMap=new HashMap<String,String>();
        List<SysConfigItem> list = sysConfigDao.fetchAllConfig();
        if(list != null){
            initCache(list);
        }
        logger.info("===========得到缓存完毕===================");
    }

    /**
     * 重新加载缓存
     */
    public synchronized void reloadAll() {
        logger.info("===========重新得到缓存开始===================");
        List<SysConfigItem> list = sysConfigDao.fetchAllConfig();
        if(list != null){
            cacheMap.clear();
            initCache(list);
        }
        logger.info("===========重新得到缓存完成===================");
    }
    
    /**
     * 手动添加一个缓存
     * @param sysConfigModel
     */
    public synchronized void add(SysConfigItem s){
        cacheMap.put(s.getDisplayName(),s.getCode());
    }
    
    /**
     * 手动更新一个缓存
     * @param sysConfigModel
     */
    public synchronized void update(SysConfigItem s) {
        cacheMap.put(s.getDisplayName(),s.getCode());
    }
    
    /**
     * 根据code值获取显示名称
     * @param code
     * @return
     */
    public synchronized String findValue(String displayName){
        if("dev".equals(System.getenv("TARGET_ENV"))){
            String val=System.getProperty(displayName);//便于开发使用
            if(val!=null) {
                return val;
            }
        }
        return cacheMap.get(displayName);
    }
    
    /**
     * 获取所有的缓存
     * @return
     */
    public synchronized HashMap<String,String> getallCache(){
        return cacheMap;
        
    }
    
    /**
     * 根据code的值移除
     * @param code
     */
    public synchronized void removeSysCache(String key) {
        cacheMap.remove(key);
    }
    
    /**
     * 加载所有缓存
     * @param list
     */
    private void initCache(List<SysConfigItem> list){
        setCacheMap(list);//加载系统配置缓存
    }
    
    /**
     * 加载系统配置缓存
     * @param list
     */
    private void setCacheMap(List<SysConfigItem> list) {
        for (SysConfigItem s : list) {
            cacheMap.put(s.getDisplayName(),s.getCode());
        }
    }
    
}