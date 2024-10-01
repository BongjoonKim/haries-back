package com.hariesbackend.login.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.service.serviceImpl.CustomUserDetailsServiceImpl;
import com.hariesbackend.utils.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String accessToken = null;

        // JWT 토큰은 "Bearer "로 시작하므로 이를 검증합니다.
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            accessToken = requestTokenHeader.substring(7);
            if (accessToken != null) {
                try {
                    Claims decodedJWT = JwtUtil.verifyToken(accessToken);
                    username = decodedJWT.getSubject();
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (RuntimeException e) {
                    // 프론트에 Refresh 토큰을 요청한다
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", "application/json");
                    headers.add("Authorization", "Bearer " + accessToken);
                    TokenDTO tokenDTO = new TokenDTO();
                    tokenDTO.setAccessToken(accessToken);

                    HttpEntity entity = new HttpEntity(tokenDTO, headers);
//                    restTemplate.exchange("frontUrl/refresh", HttpMethod.POST, entity, String.class);

                    System.out.println("JWT Token has expired");
                } catch (Exception e) {
                    System.out.println("Unable to get JWT Token");
                }
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 토큰을 얻으면 검증을 시작합니다.
        if (username != null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            // 토큰이 유효한 경우 수동으로 인증을 설정합니다.
            if (jwtTokenUtil.validateToken(accessToken)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
