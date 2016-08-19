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

import com.lip.admin.common.model.VerifyCodeModel;
import com.lip.core.dao.BaseDao;

/**
 * 
 * Copyright (C)
 * 
 * Description: 验证码
 * 
 * @author lip
 * @version 1.0
 * 
 */
public interface IVerifyCodeDao extends BaseDao<VerifyCodeModel> {
    /**
     * 
     * Description: 根据类型与发送目标查询最大的一条记录
     * 
     * @param
     * @return VerifyCodeModel
     * @throws
     * @Author lip Create Date: 2014-12-16 下午4:58:55
     */
    public VerifyCodeModel selectByTypeAndTarget(String type, String userType,String target);
    
    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午11:19:36
     */
    public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType,String sendTarget, String verifyType);

	public String getInviteContent(String nodeCode);
	public int invalidVerifyCode(String userId);//将该用户的验证码失效
	public int deleteVerifyCodeByUserId(String userId);//将该用户的验证码删掉
	
	public int getSendCodeNum(VerifyCodeModel model);
}