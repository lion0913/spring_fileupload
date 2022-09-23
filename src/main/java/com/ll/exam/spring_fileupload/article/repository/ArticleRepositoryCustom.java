package com.ll.exam.spring_fileupload.article.repository;

import com.ll.exam.spring_fileupload.article.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> listByCriteria(String kwType, String kw);
}
