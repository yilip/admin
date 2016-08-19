package com.lip.admin.common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.core.convert.converter.Converter;

public class CalendarConverter implements Converter<String, Calendar> {

    public Calendar convert(String source) {
        Date date = null;
        if (source == null || source.length() == 0) {
            return null;
        } else if (source.length() == 10) {
            date = Date_Calendar_utils.stringToDate(source, Date_Calendar_utils.DEFAULT_DATE_FORMAT);
        } else if (source.length() == 19) {
            try {
                date = Date_Calendar_utils.toUtilDateFromStrDateByFormat(source, Date_Calendar_utils.DEFAULT_TIME_FORMAT);
            } catch (ParseException e) {
                throw new IllegalArgumentException("string conveter to time error, source is[" + source + "]", e);
            }
        } else {
            throw new IllegalArgumentException("string conveter to time error, unsupport this format, source is[" + source + "]");
        }
        Calendar result = null;
        if (date != null) {
            result = GregorianCalendar.getInstance();
            result.setTime(date);
        }
        return result;
    }
    
}