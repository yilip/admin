/**
 * 
 */
package com.lip.core.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lip
 * @date 2015年12月29日
 */
public class PKGenarator {
    private static final Logger logger = LoggerFactory.getLogger(PKGenarator.class);
    /**
     * 获得一个UUID
     * 
     * @return String UUID
     */
    public static String getId() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }
    
    
}

