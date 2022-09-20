package com.ll.exam.spring_fileupload.article.entity;

import com.ll.exam.spring_fileupload.entity.BaseEntity;
import com.ll.exam.spring_fileupload.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @ManyToOne
    private Member author;

    @Column(unique = true)
    private String subject;

    private String content;
}
