package com.lip.admin.common.service;

import java.util.Map;

import javax.annotation.Resource;

import com.lip.admin.common.dao.IOperateLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lip.core.model.SearchResult;

@Service("logService")
public class LogServiceImpl implements ILogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //审计日志
    @Resource
    private IOperateLogDao operateLogDao;
   
    /**
     * Description: 操作日志列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午10:04:22
     */
    public SearchResult<Map<String, Object>> getOperateLogPage(Map<String, Object> params) {
        return this.operateLogDao.getPage("getCount", "select", params);
    }
}