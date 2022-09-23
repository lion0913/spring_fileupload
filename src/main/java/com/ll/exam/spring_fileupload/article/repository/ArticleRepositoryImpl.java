package com.ll.exam.spring_fileupload.article.repository;


import com.ll.exam.spring_fileupload.article.entity.Article;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ll.exam.spring_fileupload.article.entity.QArticle.article;
import static com.ll.exam.spring_fileupload.hashtag.entity.QHashTag.hashTag;
import static com.ll.exam.spring_fileupload.keyword.entity.QKeyword.keyword;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> listByCriteria(String kwType, String kw) {
        return jpaQueryFactory
                .select(article)
                .distinct()
                .from(keyword)
                .innerJoin(hashTag)
                    .on(hashTag.keyword.eq(keyword))
                .innerJoin(hashTag.article, article)
                .where(eqContent(kwType, kw))
                .fetch();
//        return null;
    }

    private BooleanExpression eqContent(String kwType, String kw) {
        if(kwType == null || kw == null) {
            return null;
        }

        if(kwType.trim().isEmpty() || kw.trim().isEmpty()) {
            return null;
        }

        if(!kwType.equals("keyword")) return null;

        return keyword.content.eq(kw);
    }
}
