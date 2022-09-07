package com.ll.exam.spring_fileupload.member.repository;

import com.ll.exam.spring_fileupload.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
}
