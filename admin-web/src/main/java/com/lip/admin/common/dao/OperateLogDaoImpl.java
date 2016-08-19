/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: OperateDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:20:54] by lip
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
import org.springframework.stereotype.Repository;

import com.lip.core.dao.BaseDaoImpl;

/**
 * Copyright (C)
 * 
 * Description:
 * 
 * @author lip
 * @version 1.0
 * 
 */
@Repository("operateLogDao")
public class OperateLogDaoImpl extends BaseDaoImpl<OperateLogModel> implements
        IOperateLogDao {

    /**
     * Description: 添加
     *
     * @param 
     * @return int
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午9:48:10
     */
    public int saveOperateLog(OperateLogModel operateLog) {
        return this.save(operateLog);
    }

}
