package com.hariesbackend.dalle.service.serviceImpl;

import com.hariesbackend.dalle.dto.DalleReqDTO;
import com.hariesbackend.dalle.dto.DalleResDTO;
import com.hariesbackend.dalle.service.DalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleServiceImpl implements DalleService {
    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.dalle-uri}")
    private String dalleUri;



    @Override
    public DalleResDTO DalleAnswer(String question) {
        URI uri = UriComponentsBuilder.fromUriString(dalleUri).build().toUri();
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        DalleReqDTO dalleReqDTO = new DalleReqDTO("dall-e-3", question, 1, "1024x1024");

        HttpEntity<DalleReqDTO> httpEntity = new HttpEntity<>(dalleReqDTO, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DalleResDTO> response = restTemplate.postForEntity(uri, httpEntity, DalleResDTO.class);
        return response.getBody();
    }

    @Override
    public List<DalleResDTO> getDalleImages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getDetails();
        System.out.println("test");
        return null;
    }

    @Override
    public DalleResDTO getDalleIamge(String id) {
        return null;
    }
}
