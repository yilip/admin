package com.lip.core.ftp;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPManagerFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(FTPManagerFactory.class);

    private static FTPManager ftpManager;
    private static FTPManager noneSecFtpManager=new FTPUtils();
    public static FTPManager getFtpManager() {
        return ftpManager;
    }

    public static FTPManager getFtpManager(boolean isNonSecureType){
        if(isNonSecureType){
            return noneSecFtpManager;
        }
        return ftpManager;
    }

    static {
        initFTPManager();
    }
    
    /**
     * 初始化缓存管理器
     */
    private static void initFTPManager() {
        String osName = SystemUtils.OS_NAME;
        logger.info("OS_NAMEO is :"+osName);
        String manager = "";
//        if (osName.indexOf("Windows")>-1) {
//            manager = "com.fbd.core.common.ftp.FTPUtils";
//        }else{ 
//            manager = "com.fbd.core.common.ftp.SFTPUtils";
//        }
        manager = "com.bijie.clearing.bank.ftp.SFTPUtils";
        try {
            ftpManager = (FTPManager) Class.forName(manager).newInstance();
        } catch (Exception e) {
            logger.warn("FTP[class=" + manager + "]  init error:", e);
        }
    }
    
}
