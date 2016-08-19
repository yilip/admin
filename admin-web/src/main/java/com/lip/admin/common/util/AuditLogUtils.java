package com.lip.admin.common.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.lip.admin.common.dao.IOperateLogDao;
import com.lip.admin.common.model.OperateLogModel;
import com.lip.admin.common.security.AdminUserDetails;
import com.lip.admin.common.security.util.MyRequestContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lip.admin.common.dao.OperateLogDaoImpl;
import com.lip.core.utils.PKGenarator;
import com.lip.core.utils.SpringUtil;

/**
 * 消息发送工具类
 */
public class AuditLogUtils {
	
	private static IOperateLogDao operateLogDao;
	private static final Logger logger = LoggerFactory.getLogger(AuditLogUtils.class);

	static{
	    operateLogDao = (OperateLogDaoImpl)SpringUtil.getBean("operateLogDao");
	}
	
	/**
	 * Description: 
	 *
	 * @param operateModel 操作模块
	 * @param operateType 操作类型
	 * @param operateResult 操作结果
	 * @param resInfo 异常信息
	 * 
	 * @return int
	 * @throws 
	 * @Author lip
	 * Create Date: 2015-3-24 下午10:45:55
	 */
	public static int log(String operateModel,String operateType,String operateResult,String resInfo){
	    int num = 0;
	    try {
	        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
	        AdminUserDetails userDetails = MyRequestContextHolder.getCurrentUser();
	        if(null==userDetails) return -1;////未登录用户（发验证码等操作，不需要记录）
	        String userId = userDetails.getAdminId();
	        String userType = SpringPropertiesHolder.getProperty("audit.user.type");
    	    String ipAddress = WebUtil.getIpAddr(request);
	        resInfo = StringUtils.trimToEmpty(resInfo);
	        OperateLogModel operateLog = new OperateLogModel(PKGenarator.getId(), userId, userType, new Date(), ipAddress, operateType, operateModel, operateResult, resInfo);
	        num = operateLogDao.saveOperateLog(operateLog);
        } catch (Exception e) {
            logger.error("", e);
        }
	    return num;
	}
	
	
	
	/**
	 * @param userId userId
	 * @param operateModel 操作模块
     * @param operateType 操作类型
     * @param operateResult 操作结果
     * @param resInfo 异常信息
     * @param request  
	 * @return
	 */
    public static int log(String userId,String operateModel,String operateType,String operateResult,String resInfo,HttpServletRequest request){
        int num = 0;
        try {
            String userType = SpringPropertiesHolder.getProperty("audit.user.type");
            String ipAddress = WebUtil.getIpAddr(request);
            resInfo = StringUtils.trimToEmpty(resInfo);
            OperateLogModel operateLog = new OperateLogModel(PKGenarator.getId(), userId, userType, new Date(), ipAddress, operateType, operateModel, operateResult, resInfo);
            num = operateLogDao.saveOperateLog(operateLog);
        } catch (Exception e) {
            logger.error("", e);
        }
        return num;
    }
    
	
	/**
	 * Description: 
	 *
	 * @param userId 用户Id
	 * @param operateModel 操作模块
     * @param operateType 操作类型
     * @param operateResult 操作结果
     * @param resInfo 异常信息
	 * @return int
	 * @throws 
	 * @Author lip
	 * Create Date: 2015-4-7 上午9:40:10
	 */
	public static int log(String userId,String operateModel,String operateType,String operateResult,String resInfo){
        int num = 0;
        try {
            HttpServletRequest request =MyRequestContextHolder.getCurrentRequest();
            String userType = SpringPropertiesHolder.getProperty("audit.user.type");
            String ipAddress = WebUtil.getIpAddr(request);
            resInfo = StringUtils.trimToEmpty(resInfo);
            OperateLogModel operateLog = new OperateLogModel(PKGenarator.getId(), userId, userType, new Date(), ipAddress, operateType, operateModel, operateResult, resInfo);
            num = operateLogDao.saveOperateLog(operateLog);
        } catch (Exception e) {
            logger.error("", e);
        }
        return num;
    }
	
	/**
     * Description: 
     *
     * @param operateModel 操作模块
     * @param operateType 操作类型
     * @param operateResult 操作结果
     * 
     * @return int
     * @throws 
     * @Author lip
     * Create Date: 2015-3-24 下午10:45:55
     */
    public static int log(String operateModel,String operateType,String operateResult){
        return log(operateModel, operateType, operateResult, null);
    }
	
}