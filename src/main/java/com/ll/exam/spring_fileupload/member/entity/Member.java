package com.ll.exam.spring_fileupload.member.entity;

import com.ll.exam.spring_fileupload.base.AppConfig;
import lombok.*;

import javax.persistence.*;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String imgPath;

    public void removeProfileImgOnStorage(Member member) {
        if(imgPath == null || imgPath.trim().length() == 0) return;

        String profileImgPath = getProfileImgPath();

        new File(profileImgPath).delete();
    }

    public String getProfileImgPath() {
        return "/gen/"+imgPath;
    }
}
