package com.hariesbackend.login.controller;

import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/naver")
    public ResponseEntity<Object> naverLogin(HttpServletRequest request) throws Exception {
        NaverDTO naverInfo = loginService.getNaverInfo(request.getParameter("code"));
        return ResponseEntity.ok("Success");
    }
}
