/**
 * 
 */
package com.lip.admin.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * @author lip
 * @date 2015年12月25日
 */
@Controller
@RequestMapping(value="/index")
public class IndexController {
    
    /**
     * 指定默认页面
     * @date 2016年1月5日
     * @return
     */
    @RequestMapping(value="/homepage")
    public String toHomePage(){
        return "layout.homepage";
    }
    @RequestMapping("authority.resource.html")
    public String toAuth(){
        return "authority.resource";
    }
    @RequestMapping(value="/test",produces = "application/json; charset=utf-8")
    public @ResponseBody Map<String,Object> test(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("nima", "nima");
        map.put("date", new Date());
        return map;
    }
}
