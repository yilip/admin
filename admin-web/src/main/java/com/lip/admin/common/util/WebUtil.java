/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: WebUtil.java 
 *
 * Created: [2014-12-10 下午7:24:24] by lip
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

package com.lip.admin.common.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C)
 * 
 * Description:
 * 
 * @author lip
 * @version 1.0
 * 
 */

public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    /**
     * 
     * Description: 获取客户端的ip
     * 
     * @param
     * @return String
     * @throws
     * @Author lip Create Date: 2014-12-10 下午7:25:03
     */
    public static String getIpAddr(HttpServletRequest request) {
        // 提取用户真实的IP地址
        String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        logger.debug("IP addr: {}", ipAddress);
        String[] ips = ipAddress.split(",");
        if (ips.length > 1) {
            ipAddress = ips[0].trim();
            logger.debug("Extract real IP addr: {}", ipAddress);
        }
        return ipAddress;
    }
}
