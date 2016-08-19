/**
 * 
 */
package com.lip.admin.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lip
 * @date 2015年12月29日
 */
public class Date_Calendar_utils {
    private static final Logger logger = LoggerFactory.getLogger(Date_Calendar_utils.class);
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 将字符型转化成日期型类型
     * @date 2015年12月29日
     * @param dateSrc
     * @param format
     * @return
     */
    public static Date stringToDate(String dateSrc, String format) {
        if (dateSrc == null) {
            return null;
        }
        if (dateSrc.equalsIgnoreCase("")) {
            return null;
        }
        String formatTemp = format;
        if (formatTemp == null || "".equals(formatTemp)) {
            formatTemp = DEFAULT_DATE_FORMAT;
        }
        java.util.Date resultDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatTemp);
            resultDate = dateFormat.parse(dateSrc);
        } catch (Exception e) {
            logger.error("", e);
            System.err.println("字符转化成日期出现异常");
        }
        return resultDate;
    }
    
    /**
     * 字符型日期转化util.Date型日期
     * 
     * @Param:p_strDate 字符型日期
     * @param p_format
     *          格式:"yyyy-MM-dd" / "yyyy-MM-dd HH:mm:ss"
     * @Return:java.util.Date util.Date型日期
     * @Throws: ParseException
     */
    public static java.util.Date toUtilDateFromStrDateByFormat(String p_strDate,
            String p_format) throws ParseException {
        java.util.Date l_date = null;
        java.text.DateFormat df = new java.text.SimpleDateFormat(p_format);
        if (p_strDate != null && (!"".equals(p_strDate)) && p_format != null
                && (!"".equals(p_format))) {
            l_date = df.parse(p_strDate);
        }
        return l_date;
    }
    
}
