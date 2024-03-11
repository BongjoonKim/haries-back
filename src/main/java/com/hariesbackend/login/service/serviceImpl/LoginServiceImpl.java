package com.hariesbackend.login.service.serviceImpl;


import ch.qos.logback.core.testUtil.RandomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.login.dto.NaverDTO;
import com.hariesbackend.login.dto.TokenDTO;
import com.hariesbackend.login.model.Tokens;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.TokensRepository;
import com.hariesbackend.login.repository.UsersRepository;
import com.hariesbackend.login.service.LoginService;
import com.hariesbackend.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.NullableUtils;
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

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TokensRepository tokensRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
//    private final PasswordEncoder passwordEncoder;


    private final static String NAVER_AUTH_URI = "https://nid.naver.com";
    private final static String NAVER_API_URI = "https://openapi.naver.com";

    // 네이버 로그인 정보 가져오기
    @Override
    public NaverDTO getNaverInfo(String code, String State, String type) throws Exception{
        if (code == null) throw new Exception("Failed get authorization code");
        String accessToken = "";
        String refreshToken = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // accessToken 추출하기
            accessToken = getAccessToken(code).get("access_token").asText();

            // 사용자 정보 가져오는 로직
            JsonNode userInfo = getUserInfo(accessToken);

            // 트리 구조 JSON 값 가져오기
            NaverDTO naverDTO = objectMapper.treeToValue(userInfo.get("response"), NaverDTO.class);

            // 사용자 정보를 가져오거나, 없을 경우 사용자 생성
            Users users = this.findByEmailOrCreate(naverDTO);

            TokenDTO tokenDTO = this.login(users.getUsername(), users.getUserPassword(), users.getEmail());
            System.out.println("tokenDTO"+ tokenDTO);

            naverDTO.setTokenDTO(tokenDTO);
            naverDTO.setRoles(users.getRoles());

            return naverDTO;
        } catch (Exception e) {
            throw new Exception("e", e);
        }
    }

    // NaverDTO를 Users로 생성
    private Users makeNaverDTOToUsers(NaverDTO naverDTO) throws Exception {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyymm-dd");
        Users users = new Users();

        try {
            users.setUserId(naverDTO.getId());
            users.setUserPassword(naverDTO.getPassword());
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

    //신규 사용자 DB에 저장
    private Users saveUsers(Users users) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        users.setActive(true);
        users.setCreated(now);
        users.setModified(now);
        try {
            usersRepository.save(users);
            return usersRepository.findByEmail(users.getEmail());
        } catch (Exception e) {
            throw e;
        }

    }


    @Override
    public String getNaverLogin() {
        return NAVER_AUTH_URI + "/oauth2.0/authorize"
                + "?client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URL
                + "&response_type=code";
    }

    @Override
    public Users findByEmailOrCreate(NaverDTO naverDTO) throws Exception {
        try {
            Users users = usersRepository.findByEmail(naverDTO.getEmail());
            if (users == null) {
                users = makeNaverDTOToUsers(naverDTO);    // Users 객체 생성
                users.setUserPassword(getRamdomPassword(20));
                users = this.saveUsers(users);
            }
            return users;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    // 토큰으로 사용자 정보 가져오기
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

    @Override
    public TokenDTO login(String userName, String password, String email) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDTO tokenDTO = jwtTokenProvider.generateToken(email);

            // 4. 이미 토큰을 가지고 있다면 업데이트 하기, 없다면 신규 생성
            Tokens tokens = tokensRepository.findByEmail(email);
            System.out.println("토큰" + tokens);
            if (tokens == null) {   // 신규 생성
                Tokens newTokens = new Tokens();
                BeanUtils.copyProperties(tokenDTO, newTokens);
                tokensRepository.save(newTokens);
            } else {    // 기존 것 엎어치기
                System.out.println("이메일");
                tokens.setAccessToken(tokenDTO.getAccessToken());
                tokens.setRefreshToken(tokenDTO.getRefreshToken());
                tokens.setEmail(email);
                // 5. 토큰 Save
                tokensRepository.save(tokens);
            }


            return tokenDTO;
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
