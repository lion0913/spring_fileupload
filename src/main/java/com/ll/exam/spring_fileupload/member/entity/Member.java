package com.ll.exam.spring_fileupload.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.spring_fileupload.base.AppConfig;
import com.ll.exam.spring_fileupload.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@ToString(callSuper = true)
public class Member extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(unique=true)
    private String username;

    @Column
    @JsonIgnore
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

    public Member(long id) {
        super(id);
    }
}
