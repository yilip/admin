package com.lip.core.utils;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

@SuppressWarnings("deprecation")
public class HashCrypt {
    private static final Logger logger = LoggerFactory.getLogger(HashCrypt.class);
    public static String SHA = "SHA";

    public static String MD5 = "MD5";

    private final static PasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();

    private final static PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    public static String getDigestHash(String str) {
        return getDigestHash(str, "SHA");
    }

    public static String getDigestHash(String str, String hashType) {
        if (str == null)
            return null;
        return getDigestHash(str, hashType, null);
    }

    public static String getDigestHash(String str, String hashType, Object salt) {
        if (hashType == null) {
            hashType = SHA;
        }

        if (hashType.equals(MD5)) {
            return md5PasswordEncoder.encodePassword(str, salt);
        }

        return shaPasswordEncoder.encodePassword(str, salt);
     }

        
     public static String getSalt(){
            SecureRandom sr;
            byte[] salt = new byte[16];
            try {
                sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
                sr.nextBytes(salt);
            } catch (Exception e) {
                logger.error("生产随机码错误", e);
            } 
            return salt.toString();
        }

}
