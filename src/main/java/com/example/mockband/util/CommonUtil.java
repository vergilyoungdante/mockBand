package com.example.mockband.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class CommonUtil {

    public synchronized static String randomBankAccount()
    {
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }

    public synchronized static File transferToFile(MultipartFile multipartFile,String userName) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileLocation = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"picture/";

            file=new File(fileLocation+userName+".jpg");
            if  (!file.exists()  && !file.isDirectory())
            {
                System.out.println("//不存在");
                file.mkdirs();
            }
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
