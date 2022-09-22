package com.ll.exam.spring_fileupload.hashtag.service;

import com.ll.exam.spring_fileupload.article.entity.Article;
import com.ll.exam.spring_fileupload.hashtag.entity.HashTag;
import com.ll.exam.spring_fileupload.hashtag.repository.HashTagRepository;
import com.ll.exam.spring_fileupload.keyword.entity.Keyword;
import com.ll.exam.spring_fileupload.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final KeywordService keywordService;

    private final HashTagRepository hashTagRepository;


    public void applyHashTags(Article article, String hashTagStr) {
        List<String > hashTagList = Arrays.stream(hashTagStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        hashTagList.forEach( hashTag -> {
            saveHashTag(article, hashTag);
        });
    }

    private HashTag saveHashTag(Article article, String keywordContent) {
        Keyword keyword = keywordService.save(keywordContent);

        Optional<HashTag> opHashTag = hashTagRepository.findByArticleIdAndKeywordId(article.getId(), keyword.getId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTag hashTag = HashTag.builder()
                .article(article)
                .keyword(keyword)
                .build();

        hashTagRepository.save(hashTag);

        return hashTag;
    }
}
