package com.lip.core.ftp;

import java.io.InputStream;

public interface FTPManager {
    
    
    boolean uploadFile(String path,String filename,String host,int port,String username,String password);
            
    
    InputStream  downloadFile(String path, String fileName,String localPath,String host,int port,String username,String password); 
}
