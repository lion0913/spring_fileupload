package com.ll.exam.spring_fileupload.base;

import com.ll.exam.spring_fileupload.security.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.disable()
                )
                .authorizeRequests( //인가
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/**")
                                .permitAll()
                )
                .formLogin( //스프링부트에서 기본적으로 제공하는 로그인 페이지를 커스텀하기 위해 사용
                        formLogin -> formLogin
                                .loginPage("/member/login") // GET
                                .loginProcessingUrl("/member/login") // POST
                )
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/member/login")
                                .userInfoEndpoint(
                                        userInfoEndpoint -> userInfoEndpoint
                                                .userService(oAuth2UserService)
                                )
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
