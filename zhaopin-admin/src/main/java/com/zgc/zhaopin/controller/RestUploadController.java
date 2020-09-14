package com.zgc.zhaopin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/upload")
public class RestUploadController {

    @PostMapping("/image")
    // @RequiresPermissions("upload")
    public int multiUpload(HttpServletRequest request) {
        // get image file from web
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("image");

        // get file name
        String filename = file.getOriginalFilename();

        // String currentPath = (new File("").getAbsolutePath());
        String currentPath = System.getProperty("user.dir");
        File currentFolder = new File(currentPath + "/upload/");
        if(!currentFolder.exists()) {
            currentFolder.mkdir();
        }

        String uploadPath = currentFolder.getAbsolutePath();
        File uploadFile = new File(uploadPath + "/" + filename);
        if(uploadFile.exists()) {
            // -1 means file has already exist
            return -1;
        }
    
        String base64Code = null;
        InputStream imageStream = null;

        try {
            // set file stream from MultipartFile
            imageStream = file.getInputStream();

            // create bytes[] which can hold file size to store in
            byte[] bytes = new byte[imageStream.available()];

            // read byte stream from MultipartFile
            imageStream.read(bytes);

            // get base64 code from image stream
            base64Code = Base64.getEncoder().encodeToString(bytes);

            file.transferTo(uploadFile);

        } catch (IOException e) {
            e.printStackTrace();
            // 0 means upload failed
            return 0;
        } finally {
            if(imageStream != null) {
                try {
                    imageStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 1 means upload successfully
        return 1;
    }

}