package com.lip.admin.common.util;

import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateTimeConverter implements Converter<String, Date> {

    public Date convert(String source) {
        Date result = null;
        if (source == null || source.length() == 0) {
            return null;
        } else if (source.length() == 10) {
            result = Date_Calendar_utils.stringToDate(source, Date_Calendar_utils.DEFAULT_DATE_FORMAT);
        } else if (source.length() == 19) {
            try {
                result = Date_Calendar_utils.toUtilDateFromStrDateByFormat(source, Date_Calendar_utils.DEFAULT_TIME_FORMAT);
            } catch (ParseException e) {
                throw new IllegalArgumentException("string conveter to time error, source is[" + source + "]", e);
            }
        } else {
            throw new IllegalArgumentException("string conveter to time error, unsupport this format, source is[" + source + "]");
        }
        return result;
    }
    
}