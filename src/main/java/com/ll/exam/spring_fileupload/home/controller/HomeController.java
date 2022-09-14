package com.ll.exam.spring_fileupload.home.controller;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    final MemberService memberService;

    @GetMapping("/")
    public String main() {
        return "home/main";
    }

    @GetMapping("/test/upload")
    public String upload() {
        return "home/test/upload";
    }

}
