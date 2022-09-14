package com.ll.exam.spring_fileupload.member.service;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email, MultipartFile profileImg) {

        String profileImgDirName = "member";
        String fileName = UUID.randomUUID().toString() + ".png";
        String profileImgDirPath = genFileDirPath + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

        try {
            profileImg.transferTo(new File(profileImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String profileImgRelPath = profileImgDirName + "/" + fileName;


        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .imgPath(profileImgRelPath)
                .build();

        memberRepository.save(member);

        return member;
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("member"));

        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member join(String username, String password, String email, String imgPath) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .imgPath(imgPath)
                .build();

        memberRepository.save(member);

        return member;
    }

    public long count() {
        return memberRepository.count();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void removeProfileImg(Member member) {
        member.removeProfileImgOnStorage(member);
        member.setImgPath(null);

        memberRepository.save(member);
    }
}
