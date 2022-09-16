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

    public void removeProfileImgOnStorage() {
        if(imgPath == null || imgPath.trim().length() == 0) return;

        String profileImgPath = getImgPath();

        new File(profileImgPath).delete();
    }

    private String getImgPath() {
        return AppConfig.GET_FILE_DIR_PATH + "/" + imgPath;
    }

    public String getProfileImgPath() {
        return "/gen/"+imgPath;
    }
}
