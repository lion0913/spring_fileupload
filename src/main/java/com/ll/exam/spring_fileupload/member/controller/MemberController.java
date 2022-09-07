package com.ll.exam.spring_fileupload.member.controller;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(String username, String password, String email, MultipartFile profileImg, HttpSession httpSession) {
        if(memberService.existsByUsername(username) == true) {
            return "이미 가입된 회원입니다";
        }

        Member member = memberService.join(username, "{noop}"+password, email, profileImg);

        httpSession.setAttribute("loginMemberId", member.getId());

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession httpSession) {
        Long memberId = (Long) httpSession.getAttribute("loginMemberId");

        boolean isLogined = (memberId != null);

        if(isLogined == false)
            return "redirect:/?errorMsg=Need to login!";

        return "/member/profile";
    }

}
