package com.ll.exam.spring_fileupload.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinForm {
    private String username;
    private String password;
    private String email;
    private MultipartFile profileImg;
}
