package com.ll.exam.spring_fileupload.keyword.repository;


import com.ll.exam.spring_fileupload.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByContent(String content);
}
