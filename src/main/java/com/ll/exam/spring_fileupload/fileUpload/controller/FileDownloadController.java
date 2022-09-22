package com.ll.exam.spring_fileupload.fileUpload.controller;


import com.ll.exam.spring_fileupload.fileUpload.entity.GenFile;
import com.ll.exam.spring_fileupload.fileUpload.service.GenFileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/download")
@AllArgsConstructor
public class FileDownloadController {

    private final GenFileService genFileService;

    @GetMapping("/gen/{id}")
    @ResponseBody
    public void downloadPost(HttpServletResponse response, @PathVariable Long id) throws IOException{
        GenFile genFile = genFileService.findById(id).get();

        String path = genFile.getFilePath();

        File file = new File(path);
        response.setHeader("Content-Disposition", "attachment;filename=" + genFile.getOriginFileName());

        FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
        OutputStream out = response.getOutputStream();

        int read = 0;
        byte[] buffer = new byte[1024];
        while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
            out.write(buffer, 0, read);
        }
    }
}
