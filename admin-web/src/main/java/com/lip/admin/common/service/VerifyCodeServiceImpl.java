/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: VerifyCodeServiceImpl.java 
 *
 * Created: [2014-12-15 下午3:41:37] by lip
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lip.core.service.SysConfigCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lip.admin.common.dao.IAdminDao;
import com.lip.admin.common.dao.IVerifyCodeDao;
import com.lip.admin.common.model.VerifyCodeModel;
import com.lip.admin.common.security.model.AdminModel;
import com.lip.admin.common.util.CoreConstants;
import com.lip.admin.common.util.MessageUtils;
import com.lip.admin.common.util.SecurityCode;
import com.lip.core.utils.PKGenarator;

/**
 * Copyright (C)
 * 
 * Description:验证码
 * 
 * @author lip
 * @version 1.0
 * 
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements IVerifyCodeService {
	@Resource
	private IVerifyCodeDao verifyCodeDao;

	@Resource
	private IAdminDao adminDao;


	/**
	 * Description: 发送验证码
	 *
	 * @param
	 * @return Map<String,Object>
	 * @throws @Author
	 *             lip Create Date: 2015-2-27 下午12:47:42
	 */
	public void sendVerifyCode(String messageNodeCode, String mobileNumber, String userId) {
		VerifyCodeModel oldModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(userId, CoreConstants.userType.A, mobileNumber, messageNodeCode);
		if(null!=oldModel){
		    // 发送验证码将以前验证码删除
	        verifyCodeDao.deleteVerifyCodeByUserId(userId);
		}
		// 生成验证码
		String securityCode = SecurityCode.getVerifyCode();
		// 发送短信验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put("authCode", securityCode);
		boolean send = MessageUtils.sendSMS(messageNodeCode, params, mobileNumber);
		// 保存验证码
		if (send) {
			// 查询管理员对象
		    AdminModel admin = new AdminModel();
			if (!StringUtils.isEmpty(userId)) {
				admin = adminDao.getAdminByUserId(userId);
			} else {
				admin = adminDao.getAdminByMobile(mobileNumber);
			}
			// 保存验证码到数据库
			VerifyCodeModel verifyCode = new VerifyCodeModel();
			verifyCode.setId(PKGenarator.getId());
			verifyCode.setUserId(admin.getAdminId());
			verifyCode.setUserType(CoreConstants.userType.A);
			verifyCode.setVerifyCode(securityCode);
			verifyCode.setVerifyType(messageNodeCode);
			verifyCode.setSendTarget(mobileNumber);
			Calendar calendar = Calendar.getInstance();
			// 设置生成时间
			verifyCode.setGenTime(calendar.getTime());
			// 设置失效时间
			String timeOut = (SysConfigCache.getInstance().findValue("verifycodeDeadLimit"));
			if (StringUtils.isEmpty(timeOut)) {
				timeOut = "10";
			}
			calendar.add(Calendar.MINUTE, Integer.parseInt(timeOut));
			verifyCode.setDeadTime(calendar.getTime());
			verifyCode.setSendResult("success");
			verifyCode.setStatus("valid");
			verifyCodeDao.save(verifyCode);
		}
	}

	/**
	 * Description: 发送验证码
	 *
	 * @param
	 * @return Map<String,Object>
	 * @throws 
	 * @Author Lip
	 *	Create Date: 2015-2-27 下午12:47:42
	 */
	public String sendVerifyCode(String messageNodeCode, String mobileNumber, String userId, String type) {
		// 发送验证码将以前验证码删除
		verifyCodeDao.deleteVerifyCodeByUserId(userId);
		// 生成验证码
		String securityCode = SecurityCode.getVerifyCode();
		// 发送短信验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put("password", securityCode);
		boolean send = MessageUtils.sendSMS(messageNodeCode, params, mobileNumber);
		if (send)
			return securityCode;
		return null;
	}
	
	/**
	 * Description: 根据userId、userType和verifyType查询有效的验证码
	 *
	 * @param
	 * @return VerifyCodeModel
	 * @throws @Author
	 *             lip Create Date: 2015-3-6 上午11:19:36
	 */
	public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId, String userType, String sendTarget,
			String verifyType) {
		return verifyCodeDao.getVerifyCodeByUserAndVerifyType(userId, userType, sendTarget, verifyType);
	}

	/**
	 * Description: 发送邮件验证码
	 *
	 * @param
	 * @return Map<String,Object>
	 * @throws @Author
	 *             lip Create Date: 2015-3-31 下午6:17:32
	 */
	public void sendEmailVerifyCode(String messageNodeCode, String email, String userId) {
		// 生成验证码
		String securityCode = SecurityCode.getVerifyCode();
		// 发送短信验证码
		Map<String, String> params = new HashMap<String, String>();
		params.put("authCode", securityCode);
		boolean send = MessageUtils.sendEmail(messageNodeCode, params, email, "验证码");
		// 保存验证码
		if (send) {
			// 查询管理员对象
			AdminModel admin = adminDao.getAdminByUserId(userId);
			// 保存验证码到数据库
			VerifyCodeModel verifyCode = new VerifyCodeModel();
			verifyCode.setId(PKGenarator.getId());
			verifyCode.setUserId(admin.getAdminId());
			verifyCode.setUserType(CoreConstants.userType.A);
			verifyCode.setVerifyCode(securityCode);
			verifyCode.setVerifyType(messageNodeCode);
			verifyCode.setSendTarget(email);
			Calendar calendar = Calendar.getInstance();
			// 设置生成时间
			verifyCode.setGenTime(calendar.getTime());
			// 设置失效时间
			String timeOut = (SysConfigCache.getInstance().findValue("verifycodeDeadLimit"));
			if (StringUtils.isEmpty(timeOut)) {
				timeOut = "10";
			}
			calendar.add(Calendar.MINUTE, Integer.parseInt(timeOut));
			verifyCode.setDeadTime(calendar.getTime());
			verifyCode.setSendResult("success");
			verifyCode.setStatus("valid");
			verifyCodeDao.save(verifyCode);
		}
	}
}
