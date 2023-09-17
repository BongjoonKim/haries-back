package com.hariesbackend.login.service;

import com.hariesbackend.login.dto.NaverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface LoginService {
    String getNaverLogin();
    NaverDTO getNaverInfo(String code) throws Exception;
}

