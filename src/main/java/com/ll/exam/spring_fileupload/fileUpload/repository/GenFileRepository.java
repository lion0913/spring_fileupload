package com.ll.exam.spring_fileupload.fileUpload.repository;


import com.ll.exam.spring_fileupload.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
}
