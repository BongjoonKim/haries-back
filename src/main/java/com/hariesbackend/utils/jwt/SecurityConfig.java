package com.hariesbackend.utils.jwt;

import com.hariesbackend.login.service.serviceImpl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
//public class SecurityConfig {
//    @Autowired
//    CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .cors();
////        http.authorizeRequests()
////                .anyRequest().permitAll();
////                .and()
////                .formLogin()
////                .loginPage("/home")
//                //.usernameParameter("username2") -> userDetailsService의 loadByUsername함수 파라미터값을 바꾸고싶을때
////                .loginProcessingUrl("/login")
////                .defaultSuccessUrl("/");
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//}
