package com.hariesbackend.login.service;

import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface LoginService {
    String getNaverLogin();
    TokenDTO login(String memberId, String password);
    UserDetails loadUserByUsername(String username);
    NaverDTO getNaverInfo(String code, String state, String type) throws Exception;
}

