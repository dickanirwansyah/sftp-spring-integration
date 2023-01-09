package com.rnd.sftpservice.sftp;

import org.junit.jupiter.api.Test;

public class UpAndDownloadTestService {

    @Test
    public void upload(){
        new UpAndDownload().upload();
    }

    @Test
    public void donwload(){
        String downloadStfp = new UpAndDownload().download();
        System.out.println("Download text\n"+downloadStfp);
    }
}
