package com.hariesbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 리액트와 Cors 문제 해결을 위한 코드
@Configuration
public class CorsConfig {
    @Value("${front.url.local}")
    private String frontLocalUrl;

    @Value("${front.url.prod}")
    private String frontProdUrl;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //리소스를 허용할 URL 지정
        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("http://localhost:2999");
        allowedOriginPatterns.add(frontLocalUrl);
        allowedOriginPatterns.add(frontProdUrl);
        allowedOriginPatterns.add("https://www.harietis.com");
        allowedOriginPatterns.add("www.harietis.com");
        allowedOriginPatterns.add("https://www.harietis.com/");
        allowedOriginPatterns.add("www.harietis.com/");
        allowedOriginPatterns.add("https://www.harietis.com:2999");
        allowedOriginPatterns.add("www.harietis.com:3002");
        allowedOriginPatterns.add("https://www.harietis.com:2999/");
        allowedOriginPatterns.add("www.harietis.com:2999/");
        configuration.setAllowedOrigins(allowedOriginPatterns);

        //허용하는 HTTP METHOD 지정
        ArrayList<String> allowedHttpMethods = new ArrayList<>();
        allowedHttpMethods.add("GET");
        allowedHttpMethods.add("POST");
        allowedHttpMethods.add("PUT");
        allowedHttpMethods.add("DELETE");
        configuration.setAllowedMethods(allowedHttpMethods);

        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));

        //인증, 인가를 위한 credentials 를 TRUE로 설정
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
