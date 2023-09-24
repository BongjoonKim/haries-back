package com.hariesbackend.login.service.serviceImpl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UserRepository;
import com.hariesbackend.login.service.LoginService;
import com.hariesbackend.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String NAVER_REDIRECT_URL;

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final static String NAVER_AUTH_URI = "https://nid.naver.com";
    private final static String NAVER_API_URI = "https://openapi.naver.com";

    @Override
    public TokenDTO login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = jwtTokenProvider.generateToken(authentication);

        return tokenDTO;
    }

    @Override
    public UserDetails findByEmailOrCreate(NaverDTO naverDTO) throws RuntimeException {
        try {
            return userRepository.findByEmail(naverDTO.getEmail())
                .map(this::createUserDetails)
                .orElseGet(() -> {
                    try {
                        Users users = makeNaverDTOToUsers(naverDTO);
                        return this.createUserDetails(users);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                });
        } catch (Exception e) {
            throw e;
        }

    }

    private Users makeNaverDTOToUsers(NaverDTO naverDTO) throws Exception {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyymm-dd");
        Users users = new Users();

        try {
            users.setActive(true);
            users.setUserId(naverDTO.getId());
            users.setNickname(naverDTO.getNickname());
            users.setProfileImg(naverDTO.getProfile_image());
            users.setAgeRange(naverDTO.getAge());
            users.setGender(naverDTO.getGender());
            users.setEmail(naverDTO.getEmail());
            users.setPhoneNumber(naverDTO.getMobile());
            users.setPhoneNumberE164(naverDTO.getMobile_e164());
            users.setUserName(naverDTO.getName());
            users.setBirthday(birthdayFormat.parse(naverDTO.getBirthyear() + naverDTO.getBirthday()));

            return users;
        } catch (Exception e) {
            throw e;
        }
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Users user) {
        return Users.builder()
                .userName(user.getUserId())
                .userPassword(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRoles())
                .build();
    }


    @Override
    public String getNaverLogin() {
        return NAVER_AUTH_URI + "/oauth2.0/authorize"
                + "?client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URL
                + "&response_type=code";
    }

    @Override
    public NaverDTO getNaverInfo(String code, String State, String type) throws Exception{
        if (code == null) throw new Exception("Failed get authorization code");
        String accessToken = "";
        String refreshToken = "";
        try {
            // 사용자 정보 가져오는 로직
            accessToken = getAccessToken(code).get("access_token").asText();
            System.out.println("userInfo" + accessToken);
            JsonNode userInfo = getUserInfo(accessToken);

            NaverDTO naverDTO = new NaverDTO();
            BeanUtils.copyProperties(userInfo.get("response"), naverDTO);

            UserDetails userDetails = this.findByEmailOrCreate(naverDTO);










            return new NaverDTO();
        } catch (Exception e) {
            throw new Exception("e", e);
        }
    }

    private JsonNode getAccessToken(String code) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", NAVER_CLIENT_ID);
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", code);
        params.add("redirect_uri", NAVER_REDIRECT_URL);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                NAVER_AUTH_URI + "/oauth2.0/token",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        String responseBody = response.getBody();
        System.out.println("responseBody" + responseBody);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode;
        } catch (Exception e) {
            throw e;
        }
    }

    private JsonNode getUserInfo(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<?> requests = new HttpEntity<>(body, headers);
        System.out.println("리퀘스트" + requests);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.GET,
                    requests,
                    String.class
            );

            response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode;
        } catch (Exception e) {
            throw e;
        }
    }
}
