package com.ll.exam.spring_fileupload.fileUpload.repository;


import com.ll.exam.spring_fileupload.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
    List<GenFile> findByRelId(Long id);
}
