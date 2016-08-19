package com.lip.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lip.admin.common.base.BaseAction;
import com.lip.admin.common.service.ILogService;
import com.lip.core.model.SearchResult;

@Controller
@Scope("prototype")
@RequestMapping("/log")
public class LogAction extends BaseAction {
	@SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory
            .getLogger(LogAction.class);
    /**
     * 
     */
    private static final long serialVersionUID = -4803963548470295353L;

  //管理员审计日志
    @Resource
    private ILogService logService;
    
    
    /**
     * Description: 操作日志列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午10:04:22
     */
    @RequestMapping(value = "/getOperateLoglist", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getOperateLogPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(this.getRequestParam());
        params.putAll(this.getPageParam());
        SearchResult<Map<String,Object>> operateLogPage =logService.getOperateLogPage(params);
        return operateLogPage;
    }
}

