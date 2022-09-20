package com.ll.exam.spring_fileupload.article.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ArticleForm {
    @NotEmpty
    private String subject;

    @NotEmpty
    private String content;
}
