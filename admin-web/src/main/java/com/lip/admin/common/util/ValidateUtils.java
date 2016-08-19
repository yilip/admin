/* 
 * Copyright (C)
 *
 * 本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的.
 *
 * ============================================================
 *
 * FileName: ValidateUtils.java 
 *
 * Created: [2014-12-9 上午10:50:58] by lip
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

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Copyright (C)
 * 
 * Description:参数验证
 * 
 * @author lip
 * @version 1.0
 * 
 */
public class ValidateUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtils.class);
    static Properties regular = null;
    static {
        Resource resource = new ClassPathResource(
                "/config/regular_expression.properties");
        try {
            regular = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("加载配置文件出错");
        }
    }

    /**
     * 
     * Description: 验证邮箱
     * 
     * @param
     * @return boolean
     * @throws
     * @Author lip Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateEmail(String param) {
        String reg = regular.getProperty("input.email");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

    /**
     * 
     * Description: 验证手机
     * 
     * @param
     * @return boolean
     * @throws
     * @Author lip
     * Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateMobile(String param) {
        String reg = regular.getProperty("input.mobile");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

    /**
     * 
     * Description: 验证用户名必须包含数字和字母，长度为6-16位
     * 
     * @param
     * @return boolean
     * @throws
     * @Author lip Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateUserId(String param) {
        String reg = regular.getProperty("input.username");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

    /**
     * 
     * Description: 验证用户名是否为长度为6-16位的纯数字
     * 
     * @param
     * @return boolean
     * @throws
     * @Author lip Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateUserIdData(String param) {
        String reg = regular.getProperty("input.username.data");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

    /**
     * 
     * Description: 验证金额（保留4位小数）
     * 
     * @param
     * @return boolean /^[1-9]\d*(\.\d+)?$/input.data
     * @throws
     * @Author lip Create Date: 2014-12-22 上午11:17:16
     */
    public static boolean isNumber(String str) {
        String reg = regular.getProperty("input.data");
        boolean rs = Pattern.compile(reg).matcher(str).find();
        return rs;
    }

    public static void main(String[] args) {
        boolean aa = ValidateUtils.isNumber("1.1");
        System.out.print(aa);
    }
}
