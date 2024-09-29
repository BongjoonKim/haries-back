package com.hariesbackend.login.controller;

import com.hariesbackend.error.CustomException;
import com.hariesbackend.login.dto.MemberLoginRequestDTO;
import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.dto.UsersDTO;
import com.hariesbackend.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/naver")
    public ResponseEntity<NaverDTO> naverLogin(
            @RequestParam("code") String code,
            @RequestParam("state") String state) throws Exception {
        NaverDTO naverDTO = loginService.getNaverInfo(code, state, "naver");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", naverDTO.getTokenDTO().getGrantType() + naverDTO.getTokenDTO().getAccessToken());
        headers.add("RefreshToken", naverDTO.getTokenDTO().getRefreshToken());
         return ResponseEntity.ok()
                 .headers(headers)
                 .body(naverDTO);
    }

    @PostMapping("/ps/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody HashMap<String, String> data
    ) {
        try {
            TokenDTO tokenDTO = loginService.refreshToken(data.get("refreshToken"));
            return ResponseEntity.ok(tokenDTO);
        } catch (CustomException e) {
            return new ResponseEntity<>(new CustomException(e.getStatus(), e.getMsg()), e.getStatus());
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UsersDTO> getLoginedUser() throws Exception {
        UsersDTO usersDTO = loginService.getUserInfo();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/ps/is/logined")
    public ResponseEntity<UsersDTO> isLogined() throws Exception {
        UsersDTO usersDTO = loginService.getUserInfo();
        return ResponseEntity.ok(usersDTO);
    }
}
