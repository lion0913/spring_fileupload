package com.ll.exam.spring_fileupload.security.dto;


import com.ll.exam.spring_fileupload.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class MemberContext extends User implements OAuth2User {
    private final Long id;
    private final String profileImgUrl;

    private final String email;

    private Map<String, Object> attributes;
    private String userNameAttributeName;

    @Setter
    private LocalDateTime modifyDate;


    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.profileImgUrl = member.getProfileImgPath();
        this.modifyDate = member.getModifyDate();
        this.email = member.getEmail();
    }

    public MemberContext(Member member, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName) {
        this(member, authorities);
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getAttribute(this.userNameAttributeName).toString();
    }

    public String getProfileImgRedirectUrl() {
        return "/member/profile/img/" + getId() + "?cacheKey=" + getModifyDate().toString();
    }

    public boolean memberIs(Member member) {
        return id.equals(member.getId());
    }
}
