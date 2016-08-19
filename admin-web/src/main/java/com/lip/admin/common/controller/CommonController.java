/**
 * 
 */
package com.lip.admin.common.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lip.admin.common.security.AdminRoleGrantedAuthority;
import com.lip.admin.common.security.AdminUserDetails;
import com.lip.admin.common.security.util.MyRequestContextHolder;

/**
 * @author lip
 * @date 2015年12月29日
 */
@Controller
public class CommonController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView redirect(){
        return new ModelAndView("layout.homepage",new HashMap<String, Object>());
    }
    /**
     * 通用的action，用于跳转到对应的页面（jsp），并使用tiles框架
     * @date 2015年12月29日
     * @param pageName
     * @param request
     * @return
     */
    @RequestMapping(value = "{pageName}.html", method = RequestMethod.GET) 
    public ModelAndView goToPage(@PathVariable("pageName") String pageName,HttpServletRequest request){
        Map<String, Object> result  = new HashMap<String, Object>();
        ///进行身份验证
        boolean flag = false;
        String parentPage = pageName;
        if(pageName.contains(".")){
            parentPage = pageName.substring(0, pageName.indexOf("."));
        }
        AdminUserDetails userDetails = MyRequestContextHolder.getCurrentUser();
        for (GrantedAuthority authority :  userDetails.getAuthorities()) {
            if (authority instanceof AdminRoleGrantedAuthority) {
                AdminRoleGrantedAuthority roleAuthority = (AdminRoleGrantedAuthority) authority;
                String resourceUrl = roleAuthority.getResourceUrl();
                if(resourceUrl.contains(parentPage)){
                    flag = true;
                    break;
                }
            }
        }
        if(false==flag){
            return new ModelAndView("illegal-access",result);
        }
        
        Iterator<?> iter = request.getParameterMap().entrySet().iterator(); 
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next(); 
            String key      = entry.getKey().toString();
            Object value    = request.getParameter(key);
            result.put(key,  value);
        }
        return new ModelAndView(pageName,result);
    }
}
