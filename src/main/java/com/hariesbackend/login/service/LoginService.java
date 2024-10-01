package com.hariesbackend.login.service;

import com.hariesbackend.error.CustomException;
import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.dto.UsersDTO;
import com.hariesbackend.login.model.Users;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    String getNaverLogin();

    String getRamdomPassword(int size);
    TokenDTO login(String memberId, String password, String email);
//    UserDetails loadUserByUsername(String username);
//    boolean findOrCreateUser(String email);
//    Users createUser(Users users);
    Users findByEmailOrCreate(NaverDTO naverDTO) throws Exception;
    NaverDTO getNaverInfo(String code, String state, String type) throws Exception;
    TokenDTO refreshToken(String refreshToken) throws CustomException, Exception;
    UsersDTO getUserInfo() throws Exception;

    boolean isLogined() throws Exception;

    void logout() throws Exception;
}

