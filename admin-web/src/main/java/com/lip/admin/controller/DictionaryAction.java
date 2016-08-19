/**
 * 
 */
package com.lip.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.model.DictionaryItem;
import com.lip.admin.service.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统配置的数据字典
 * @author lip
 * @date 2015年12月30日
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryAction {
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 根据字段类型查询,比如传入auth_permission_type可以获取授权资源类型
     * @date 2015年12月31日
     * @param typeCode
     * @return
     */
    @RequestMapping(value = "/getByType/{typeCode}")
    public @ResponseBody List<Map<String, Object>> getData4combobox(@PathVariable("typeCode") String typeCode){
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        List<DictionaryItem>  list = this.dictionaryService.selectByType(typeCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", "");
        map.put("text", "-=请选择=-");
        map.put("select", true);
        result.add(map);
        for (DictionaryItem DictionaryItem : list) {
            map = new HashMap<String, Object>();
            map.put("value", DictionaryItem.getCode());
            map.put("text", DictionaryItem.getDisplayName());
            result.add(map);
        }
        return result;
    }
}
