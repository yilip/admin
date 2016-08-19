package com.lip.admin.common.tiles;

import java.util.Map;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

public class RequestSettingViewPreparer implements ViewPreparer {

    public void execute(TilesRequestContext tilesContext,AttributeContext attributeContext) {
//        Map<String, Object> requestScope = tilesContext.getRequestScope();
        
        String viewName = attributeContext.getAttribute("content").toString();
        
        //前端页面解析
        if (viewName!=null && !"".equals(viewName)) {
            if (viewName.indexOf("WEB-INF")>-1) {
                viewName=viewName.substring(viewName.lastIndexOf("/")+1,viewName.lastIndexOf("."));
            }else{
                viewName=viewName.substring(6,viewName.length()-4).replace("/", ".");
            }
        }
    }

}
