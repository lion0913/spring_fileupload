package com.ll.exam.spring_fileupload.article.repository;

import com.ll.exam.spring_fileupload.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom{

}
