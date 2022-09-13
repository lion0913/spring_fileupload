package com.ll.exam.spring_fileupload.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/kakao")
    public String kakaoLogin() {
//        System.out.println(code);
        System.out.println("안녕");
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=532884bfb092b23c4e1eeb0c88a5124a");
        url.append("&redirect_uri=http://localhost:8010/auth/callback");
        url.append("&response_type=code");

        return "redirect:"+url.toString();
    }

    @GetMapping(value="/callback")
    public String kakaoLogin(@RequestParam("code")String code) {

        System.out.println("kakao code:"+code);

        return "member/kakao";
    }

    @GetMapping("/token")
    public String getToken(@RequestParam("code")String code) {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/token?");
        url.append("client_id=532884bfb092b23c4e1eeb0c88a5124a");
        url.append("&redirect_uri=http://localhost:8010/auth/callback");
        url.append("&grant_type=authorization_code");
        url.append("&code=%s".formatted(code));

        return "redirect:"+url.toString();
    }

    @PostMapping("/userInfo")
    public String getUserInfo(String code) {
        StringBuffer url = new StringBuffer();
        url.append("https://kapi.kakao.com/v2/user/me");
        url.append("&Authorization=Bearer %s".formatted(code));

        return "redirect:"+url.toString();
    }
}
