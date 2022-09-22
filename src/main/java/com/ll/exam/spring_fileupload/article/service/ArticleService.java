package com.ll.exam.spring_fileupload.article.service;

import com.ll.exam.spring_fileupload.article.entity.Article;
import com.ll.exam.spring_fileupload.article.repository.ArticleRepository;
import com.ll.exam.spring_fileupload.fileUpload.entity.GenFile;
import com.ll.exam.spring_fileupload.fileUpload.service.GenFileService;
import com.ll.exam.spring_fileupload.hashtag.service.HashTagService;
import com.ll.exam.spring_fileupload.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final GenFileService genFileService;

    private final HashTagService hashTagService;

    public Article write(Long authorId, String subject, String content) {
        return write(new Member(authorId), subject, content);
    }

    public Article write(Member author, String subject, String content) {
        return write(author, subject, content, "");
    }

    public Article write(Member author, String subject, String content, String hashTagStr) {
        Article article = Article
                .builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        hashTagService.applyHashTags(article, hashTagStr);

        return article;
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void addGenFileByUrl(Article article, String typeCode, String type2Code, int fileNo, String url) {
        genFileService.addGenFileByUrl("article", article.getId(), typeCode, type2Code, fileNo, url);
    }

    public Article getForPrintArticleById(Long id) {
        Article article = getArticleById(id);

        Map<String, GenFile> genFileMap = genFileService.getRelGenFileMap(article);


        article.getExtra().put("age__name__33", 22);
        article.getExtra().put("genFileMap", genFileMap);

        return article;
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);

        articleRepository.save(article);
    }
}
