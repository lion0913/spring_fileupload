package com.ll.exam.spring_fileupload.member.service;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.repository.MemberRepository;
import com.ll.exam.spring_fileupload.util.Util;
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
public class MemberService {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;

    private String saveProfileImg(MultipartFile profileImg) {
        String profileImgDirName = getCurrentProfileImgDirName();

        String ext = Util.file.getExt(profileImg.getOriginalFilename());

        String fileName = UUID.randomUUID() + "." + ext;
        String profileImgDirPath = genFileDirPath + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

        new File(profileImgDirPath).mkdirs(); // 폴더가 혹시나 없다면 만들어준다.

        try {
            profileImg.transferTo(new File(profileImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profileImgDirName + "/" + fileName;
    }

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        String profileImgRelPath = saveProfileImg(profileImg);

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


    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    private String getCurrentProfileImgDirName() {
        return "member/"+ Util.date.getCurrentDateFormatted("yyyy_MM_dd");
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


    public long count() {
        return memberRepository.count();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void removeProfileImg(Member member) {
        member.removeProfileImgOnStorage();
        member.setImgPath(null);

        memberRepository.save(member);
    }

    public void setProfileImgByUrl(Member member, String url) {
        String filePath = Util.file.downloadImg(url, genFileDirPath+"/"+getCurrentProfileImgDirName()+"/"+UUID.randomUUID());
        member.setImgPath(getCurrentProfileImgDirName() + "/" + new File(filePath).getName());

        memberRepository.save(member);
    }



    public void modify(Member member, String email, MultipartFile profileImg) {
        removeProfileImg(member);

        String profileImgRelPath = saveProfileImg(profileImg);
        member.setEmail(email);
        member.setImgPath(profileImgRelPath);
        memberRepository.save(member);
    }
}
