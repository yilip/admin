/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: AdminDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:14:46] by lip
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.lip.admin.common.dao;

import com.lip.admin.common.model.OperateLogModel;
import com.lip.core.dao.BaseDao;

/**
 * 
 * Copyright (C)
 * 
 * Description: 操作日志
 * 
 * @author lip
 * @version 1.0
 * 
 */
public interface IOperateLogDao extends BaseDao<OperateLogModel> {
    
    /**
     * Description: 添加
     *
     * @param 
     * @return int
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午9:48:10
     */
    public int saveOperateLog(OperateLogModel operateLog);
}