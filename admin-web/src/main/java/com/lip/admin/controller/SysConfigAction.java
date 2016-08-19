/**
 * 
 */
package com.lip.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lip.core.model.SysConfigItem;
import com.lip.core.service.SysConfigCache;
import com.lip.core.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lip.admin.common.base.BaseAction;
import com.lip.admin.common.base.SearchResult;
import com.lip.admin.common.util.AuditLogConstants;
import com.lip.admin.common.util.AuditLogUtils;

/**
 * @author lip
 * @date 2016年1月8日
 */
@Controller
@RequestMapping("/sysconfig")
public class SysConfigAction extends BaseAction{
    @Autowired
    private SysConfigService sysConfigService;
    
    /**
     * 根据条件查询出系统配置list
     * @date 2016年1月8日
     * @param sysConfigItem
     * @return
     */
    @RequestMapping(value = "/getlist", method = RequestMethod.POST)
    public @ResponseBody SearchResult<SysConfigItem> getbusConfigList(SysConfigItem sysConfigItem){
        List<SysConfigItem> sysConfigList = sysConfigService.getSysConfigList(sysConfigItem);
        return (SearchResult<SysConfigItem>) toSearchResult(sysConfigList);
        
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveSysConfig(SysConfigItem sysConfigItem){
        int num = sysConfigService.saveSysConfig(sysConfigItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigItem.getDisplayName()+",值："+sysConfigItem.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public @ResponseBody SysConfigItem getSysConfigById(String id){
        SysConfigItem sysConfigItem = new SysConfigItem();
        sysConfigItem.setId(id);
        List<SysConfigItem> list = sysConfigService.getSysConfigList(sysConfigItem);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyBusConfig(SysConfigItem sysConfigItem){
        int num = sysConfigService.modifySysConfig(sysConfigItem);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigItem.getDisplayName()+",值："+sysConfigItem.getCode());
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeBusConfig(String id){
        SysConfigItem sysConfigItem = getSysConfigById(id);
        int num = sysConfigService.removeSysConfig(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_SYSCONFIG, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "名称："+sysConfigItem.getDisplayName()+",值："+sysConfigItem.getCode());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 重新加载系统配置缓存
     * @return
     */
    @RequestMapping(value = "/reloadCache", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> reloadCache(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(SUCCESS, false);
        
        try {
            SysConfigCache.getInstance().reloadAll();
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CONFIG, AuditLogConstants.TYPE_MODIFY, 
                    AuditLogConstants.RESULT_SUCCESS,"重新加载系统配置缓存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    } 


}
