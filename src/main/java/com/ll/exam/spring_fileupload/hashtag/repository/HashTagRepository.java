package com.ll.exam.spring_fileupload.hashtag.repository;

import com.ll.exam.spring_fileupload.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByArticleIdAndKeywordId(Long articleId, Long keywordId);
}
