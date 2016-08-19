/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: VerifyCodeDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:21:44] by lip
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

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lip.admin.common.model.MessageTemplateModel;
import com.lip.admin.common.model.VerifyCodeModel;
import com.lip.core.dao.BaseDaoImpl;

/**
 * Copyright (C)
 * 
 * Description: 验证码
 * 
 * @author lip
 * @version 1.0
 * 
 */
@Repository("verifyCodeDao")
public class VerifyCodeDaoImpl extends BaseDaoImpl<VerifyCodeModel> implements
        IVerifyCodeDao {
    /**
     * 
     * Description: 根据类型与发送目标查询最大的一条记录
     * 
     * @param
     * @return VerifyCodeModel
     * @throws
     * @Author lip Create Date: 2014-12-16 下午4:58:55
     */
    public VerifyCodeModel selectByTypeAndTarget(String type, String userType,String userId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("verifyType", type);
        params.put("userId", userId);
        params.put("userType", userType);
        return this.selectOneByField("selectByTypeAndTarget", params);
    }

    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午11:19:36
     */
    public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType, String sendTarget, String verifyType) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("userType", userType);
        params.put("sendTarget", sendTarget);
        params.put("verifyType", verifyType);
        return this.selectOneByField("selectByTypeAndTarget", params);
    }

	public String getInviteContent(String nodeCode) {
		MessageTemplateModel messageTpl = sqlSession.selectOne("com.lip.admin.common.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
        String content = messageTpl.getTplContent();
		return content;
	}

	@Override
	public int invalidVerifyCode(String userId) {
		return this.update("invalidVerifyCode", userId);
		
	}

	@Override
	public int deleteVerifyCodeByUserId(String userId) {
		return this.deleteByField("deleteVerifyCodeByUserId", userId);
	}

	@Override
	public int getSendCodeNum(VerifyCodeModel model) {
		return (int) this.getCount("getCount", model);
	}

}
