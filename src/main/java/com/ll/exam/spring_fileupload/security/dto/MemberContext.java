package com.ll.exam.spring_fileupload.security.dto;


import com.ll.exam.spring_fileupload.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberContext extends User {
    private final Long id;
    private final String profileImgUrl;

    private final String email;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.profileImgUrl = member.getProfileImgPath();
        this.email = member.getEmail();
    }
}
