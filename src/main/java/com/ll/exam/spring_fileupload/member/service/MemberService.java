package com.ll.exam.spring_fileupload.member.service;

import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        String profilePath = null;
        if(profileImg.isEmpty() == false) {
            profilePath = "member/" + username + ".png";
            File imgFile = new File("%s/%s".formatted(genFileDirPath, profilePath));

            imgFile.mkdirs();
            try {
                profileImg.transferTo(imgFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .imgPath(profilePath)
                .build();

        memberRepository.save(member);

        return member;
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}
