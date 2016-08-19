package com.lip.core.utils;

import java.util.Calendar;

public class CalendarUtils {

    public static Calendar getTimelessToday() {
        Calendar timelessToday = Calendar.getInstance();
        timelessToday.set(Calendar.HOUR_OF_DAY, 0);
        timelessToday.set(Calendar.MINUTE, 0);
        timelessToday.set(Calendar.SECOND, 0);
        timelessToday.set(Calendar.MILLISECOND, 0);

        return timelessToday;
    }

    public static Calendar getTimelessTmr() {
        Calendar timelessTmr = Calendar.getInstance();
        timelessTmr.add(Calendar.DAY_OF_YEAR, 1);
        timelessTmr.set(Calendar.HOUR_OF_DAY, 0);
        timelessTmr.set(Calendar.MINUTE, 0);
        timelessTmr.set(Calendar.SECOND, 0);
        timelessTmr.set(Calendar.MILLISECOND, 0);

        return timelessTmr;
    }
    public static  void main(String arg[])
    {
    	Calendar calendar=getTimelessTmr();
    	System.out.println(calendar.getTime());
    }
}
