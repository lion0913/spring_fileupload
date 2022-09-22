package com.ll.exam.spring_fileupload.hashtag.entity;

import com.ll.exam.spring_fileupload.article.entity.Article;
import com.ll.exam.spring_fileupload.base.entity.BaseEntity;
import com.ll.exam.spring_fileupload.keyword.entity.Keyword;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HashTag extends BaseEntity {
    @ManyToOne
    private Article article;

    @ManyToOne
    private Keyword keyword;
}
