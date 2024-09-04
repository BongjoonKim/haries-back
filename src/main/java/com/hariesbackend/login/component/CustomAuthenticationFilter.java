package com.hariesbackend.login.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.utils.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/ps/login"); // Set custom login URL if needed
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("request = " + request);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return authentication;
    }

    // 로그인 성공
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetails usersDTO = (UserDetails) authResult.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(usersDTO.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(usersDTO.getUsername());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(accessToken);
        tokenDTO.setRefreshToken(refreshToken);
        tokenDTO.setGrantType(usersDTO.getAuthorities());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshToken);
        response.setHeader("authorities", usersDTO.getAuthorities().toString());
        new ObjectMapper().writeValue(response.getOutputStream(), tokenDTO);
    }

    // 로그인 실패
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("error", failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}