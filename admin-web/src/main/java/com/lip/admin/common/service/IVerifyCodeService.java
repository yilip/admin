/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: VerifyCodeService.java 
 *
 * Created: [2014-12-15 下午3:41:13] by lip
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

import com.lip.admin.common.model.VerifyCodeModel;

/**
 * Copyright (C)
 * 
 * Description:验证码
 * 
 * @author lip
 * @version 1.0
 * 
 */

public interface IVerifyCodeService {

    /**
     * Description: 发送验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author Lip
     * Create Date: 2015-2-27 下午12:47:42
     */
    void sendVerifyCode(String messageNodeCode,String mobileNumber,String userId);
    
    String sendVerifyCode(String messageNodeCode,String mobileNumber,String userId,String type);
    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author lip
     * Create Date: 2015-3-6 上午11:19:36
     */
    VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType,String sendTarget,String verifyType);


    /**
     * Description: 发送邮件验证码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-3-31 下午6:17:32
     */
    void sendEmailVerifyCode(String messageNodeCode, String email,String userId);
    
}
