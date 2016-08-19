package com.lip.core.ftp;

import java.io.*;

import com.lip.core.service.SysConfigCache;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPUtils implements FTPManager {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtils.class);
    
    private FTPClient ftp = null;

    /**
     * Description: 创建FTP连接
     *
     * @param 
     * @return void
     * @throws 
     * @Author lip
     * Create Date: 2015-4-14 下午3:52:22
     */
    public void connect(String host,int port,String username,String password) {
//        String url = SysConfigCache.getInstance().findValue("ftp_hostname");
//        int port = Integer.parseInt(SysConfigCache.getInstance().findValue("ftp_port"));
//        String username = SysConfigCache.getInstance().findValue("ftp_username"); 
//        String password = SysConfigCache.getInstance().findValue("ftp_password");
        if(ftp != null){
            logger.info("ftp is not null");
        }
        int reply;
        try {
            ftp = new FTPClient();
            ftp.enterLocalPassiveMode();
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.connect(host, port);//连接FTP服务器
            ftp.setControlEncoding("UTF-8");
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    
    /**
     * Description: 关闭FTP连接
     *
     * @param 
     * @return void
     * @throws 
     * @Author lip
     * Create Date: 2015-4-14 下午3:52:41
     */
    public void disconnect() {
        try {
            if (ftp != null) {
                ftp.logout();
                if (ftp.isConnected()) {
                    ftp.disconnect();
                    logger.info("ftp is closed already.");
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    private String getPath (String path){
        String basePath = SysConfigCache.getInstance().findValue("ftp_basePath");
        if (path.startsWith("/")) {
            path = basePath + path.substring(1);
        }else{
            path = basePath + path;
        }
        return path;
    }
    
    
    public synchronized boolean uploadFile(String path,String filename,String host,int port,String username,String password){
        boolean success = false;
        try {
            logger.info("-----upload file-----------");
            logger.info("-----begin connect-----------");
            connect(host, port, username, password);
            logger.info("-----connect finished-----------");
            ftp.changeWorkingDirectory(path);
            logger.info("-----cd path-----------");

            File file = new File(filename);
            logger.info("fileName: "+ filename);
            ftp.storeFile(file.getName(),new FileInputStream(file));
            logger.info("-----ftp put file-----------");
            success = true;
        } catch (IOException e) {
            logger.error("FTP 上传文件出错： ", e );
            success = false;
        } finally {
            disconnect();
        }

        return false;
    }
    
    
    
    public synchronized InputStream  downloadFile(String path, String fileName,String localPath,String host,int port,String username,String password){
        InputStream is = null;
        OutputStream os = null;
        try {
            logger.info("-----begin connect-----------");
            connect(host,port,username,password);
            logger.info("-----connect finished------------");
            ftp.changeWorkingDirectory(path);
            logger.info("-----cd path-------------");
            is = ftp.retrieveFileStream(fileName);
            logger.info("ftp get file:" + fileName);
            File localFile = new File(localPath + File.separator + fileName);
            logger.info("localFile:" + localFile);
            os = new FileOutputStream(localFile);
            byte[] buf = new byte[2048];
            int length = is.read(buf);
            logger.info("-----begin write-----------");
            while (length != -1) {
                os.write(buf, 0, length);
                length = is.read(buf);
            }
            logger.info("-----write finished-----------");
            is.close();
            os.close();
        } catch (Exception e) {
            logger.error("在FTP下载文件时报错： ", e);
        }finally{
            if(is != null){
                try{
                    is.close();
                }catch (Exception e) {
                    logger.error("关闭输入流报错： ", e);
                }
            }
            if(os != null){
                try{
                    os.close();
                }catch (Exception e) {
                    logger.error("关闭输出流报错： ", e);
                }
            }

            disconnect();
        }
        return null;
    }
}
