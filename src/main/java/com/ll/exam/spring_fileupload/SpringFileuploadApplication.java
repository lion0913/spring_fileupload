package com.ll.exam.spring_fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringFileuploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFileuploadApplication.class, args);
	}

}
