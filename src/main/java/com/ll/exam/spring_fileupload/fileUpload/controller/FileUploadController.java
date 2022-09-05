package com.ll.exam.spring_fileupload.fileUpload.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class FileUploadController {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadPost(@RequestParam("img1") MultipartFile img1){
        File file = new File(genFileDirPath+"/1.png");

        try {
            img1.transferTo(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return "업로드 완료";
    }
}
