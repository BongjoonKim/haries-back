package com.hariesbackend.login.service;

import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.model.Users;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    String getNaverLogin();
    TokenDTO login(String memberId, String password);
//    UserDetails loadUserByUsername(String username);
//    boolean findOrCreateUser(String email);
//    Users createUser(Users users);
    UserDetails findByEmailOrCreate(NaverDTO naverDTO) throws RuntimeException;
    NaverDTO getNaverInfo(String code, String state, String type) throws Exception;
}

