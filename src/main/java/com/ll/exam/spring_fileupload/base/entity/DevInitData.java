package com.ll.exam.spring_fileupload.base.entity;


import com.ll.exam.spring_fileupload.article.entity.Article;
import com.ll.exam.spring_fileupload.article.service.ArticleService;
import com.ll.exam.spring_fileupload.member.entity.Member;
import com.ll.exam.spring_fileupload.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev") // 이 클래스 정의된 Bean 들은 test 모드에서만 활성화 된다.
public class DevInitData {
    // CommandLineRunner : 주로 앱 실행 직후 초기데이터 세팅 및 초기화에 사용
    @Bean
    CommandLineRunner init(MemberService memberService,  ArticleService articleService, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");
            Member member1 = memberService.join("1", password, "user1@test.com");
            memberService.setProfileImgByUrl(member1, "https://i.picsum.photos/id/454/200/300.jpg?hmac=wGXBDB3HURz2isRdLrgggeWdD1yO3rdX4B3-jlzRncg");
            Member member2 = memberService.join("2", password, "user2@test.com");
            memberService.setProfileImgByUrl(member2, "https://i.picsum.photos/id/779/200/300.jpg?hmac=DmFN06G0c1N5TAbj2O9YljZ0Vr8VWOZ4lPjLG8oAf8o");

//            Article article1 = articleService.write(member1, "제목1", "내용1");
            Article article1 = articleService.write(member1, "제목1", "내용1", "#자바 #프로그래밍");
            articleService.addGenFileByUrl(article1, "common", "inBody", 1, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 2, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 3, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 4, "https://picsum.photos/200/300");

//            Article article2 = articleService.write(member1, "제목2", "내용2");
            Article article2 = articleService.write(member1, "제목1", "내용1", "#HTML #프로그래밍");
            articleService.addGenFileByUrl(article2, "common", "inBody", 1, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article2, "common", "inBody", 2, "https://picsum.photos/200/300");
        };
    }
}
