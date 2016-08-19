package com.lip.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * Case insensitive && string trimmed match
	 * 
	 * @param string1
	 * @param string2
	 * @return true if strings seem to have
	 */
	public static boolean isSameTextValue(String string1, String string2) {

		if (string1 == null || string2 == null) {
			if (string1 == null && string2 == null)
				return true;
			else
				return false;
		}

		if (string1.trim().equalsIgnoreCase(string2.trim())) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(String s) {
		boolean flg = true;
		if (s != null && s.trim().length() > 0) {
			return false;
		}

		return flg;
	}
	
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
	
	/**
	 * <pre><b>判断是否为手机号码,国家号码段分配如下：</b>
	 * 
　　   * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
　　   * 联通：130、131、132、152、155、156、185、186
　　   * 电信：133、153、180、189、（1349卫通）</pre>
	 * @date 2016年1月12日
	 * @param mobile
	 * @author lip
	 * @return
	 */
	public static boolean isMobile(String mobile){
	    if(null==mobile) return false;
	    
	    String regex = "^(1[3|4|5|7|8][0-9])\\d{8}$";
	    Pattern p = Pattern.compile(regex);
	    Matcher match = p.matcher(mobile);
	    return match.matches();
	}

	@SuppressWarnings("unused")
    public static void main(String[] args) throws ParseException {
        String mobile = "17072387205";
        System.out.println(isMobile(mobile));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sdf.parse("2016-1-13 09:43:00");
        Date d2 = sdf.parse("2016-1-14 09:43:01");
        System.out.println(isDateDiffWithinDays(d2, d1, 1));
        Integer i = null ;
        System.out.println(0==i);
        
        
    }

    /**
     * <pre>判断两个日期是否相差days天以内，是则放回true，否则false</pre>
     * @date 2016年1月12日
     * @param largeDate 较大日期
     * @param smallDate 较小日期
     * @param days 相差天数
     * @return
     */
    public static boolean isDateDiffWithinDays(Date largeDate, Date smallDate, int days) {
        Calendar cal = Calendar.getInstance();    
        cal.setTime(largeDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(smallDate);    
        long time2 = cal.getTimeInMillis();         
        return (time1-time2)<=(1000*3600*24*days);
    }
}
