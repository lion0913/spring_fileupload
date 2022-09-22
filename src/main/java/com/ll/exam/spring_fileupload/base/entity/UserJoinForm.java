package com.ll.exam.spring_fileupload.base.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserJoinForm {

    private String username;

    private String email;

    private String password1;
    private String password2;

    private MultipartFile img;
}
