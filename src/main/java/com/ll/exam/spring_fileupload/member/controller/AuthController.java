package com.ll.exam.spring_fileupload.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code) {
        System.out.println(code);
        return "member/kakao";
    }
}
