package com.rnd.sftpservice.sftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Slf4j
public class UpAndDownload {

    private DefaultSftpSessionFactory defaultSftpSessionFactory(){
        log.info("initiate sftp session..");
        DefaultSftpSessionFactory sftpSessionFactory = new DefaultSftpSessionFactory();
        sftpSessionFactory.setHost("0.0.0.0");
        sftpSessionFactory.setPort(22);
        sftpSessionFactory.setAllowUnknownKeys(true);
        sftpSessionFactory.setUser("foo");
        sftpSessionFactory.setPassword("pass");
        return sftpSessionFactory;
    }

    public void upload(){
        try{
            SftpSession sftpSession = defaultSftpSessionFactory().getSession();
            InputStream inputStream =  UpAndDownload.class.getClassLoader().getResourceAsStream("upload_to_sftp.txt");
            sftpSession.write(inputStream, "/upload/mynewfile"+ LocalDateTime.now()+".txt");
            sftpSession.close();
        }catch (IOException e){
            log.error("error upload file to sftp={}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public String download(){
        try{
            SftpSession sftpSession = defaultSftpSessionFactory().getSession();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            sftpSession.read("upload/download_me.txt", outputStream);
            return new String(outputStream.toByteArray());
        }catch (IOException e){
            log.error("error download file sftp={}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
