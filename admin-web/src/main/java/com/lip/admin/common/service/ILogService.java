/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: IUserLogService.java 
 *
 * Created: [2014-12-10 下午6:38:27] by lip
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.lip.admin.common.service;

import java.util.Map;

import com.lip.core.model.SearchResult;


/**
 * Copyright (C)
 * 
 * Description:
 * 
 * @author lip
 * @version 1.0
 * 
 */

public interface ILogService {
    /**
     * Description: 操作日志列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午10:04:22
     */
    SearchResult<Map<String, Object>> getOperateLogPage(Map<String, Object> params);

}
