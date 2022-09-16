package com.ll.exam.spring_fileupload.member.controller;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.service.MemberService;
import com.ll.exam.spring_fileupload.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(HttpServletRequest req, String username, String password, String email, MultipartFile profileImg, HttpSession httpSession) {
        if(memberService.existsByUsername(username) == true) {
            return "이미 가입된 회원입니다";
        }
        String passwordClearText = password;
        password = passwordEncoder.encode(password);

        Member member = memberService.join(username, password, email, profileImg);

//        httpSession.setAttribute("loginMemberId", member.getId());
        try {
            req.login(username, passwordClearText);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile() {

        return "member/profile";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "/home/about";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/currentUser")
    @ResponseBody
    public UserDetails currentUser(@AuthenticationPrincipal MemberContext memberContext) {
        return memberContext;
    }

    @GetMapping("/profile/img/{id}")
    public ResponseEntity<Object> showProfileImg(@PathVariable Long id) throws URISyntaxException {
        URI redirectUri = new URI(memberService.findById(id).getProfileImgPath());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        httpHeaders.setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String getModify(Principal principal) {
        return "/member/modify";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext context, String email, MultipartFile profileImg, String profileImg__delete) {
        Member member = memberService.findById(context.getId());

        if(profileImg__delete != null && profileImg__delete.equals("Y")) {
            memberService.removeProfileImg(member);
        }
        memberService.modify(member, email, profileImg);

        return "redirect:/member/profile";
    }

}
