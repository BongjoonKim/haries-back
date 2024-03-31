package com.hariesbackend.dalle.service.serviceImpl;

import com.hariesbackend.dalle.dto.DalleDTO;
import com.hariesbackend.dalle.dto.DalleReqDTO;
import com.hariesbackend.dalle.dto.DalleResDTO;
import com.hariesbackend.dalle.model.Dalle;
import com.hariesbackend.dalle.repository.DalleRepository;
import com.hariesbackend.dalle.service.DalleService;
import com.hariesbackend.utils.aws.S3utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleServiceImpl implements DalleService {
    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.dalle-uri}")
    private String dalleUri;

    @Autowired
    DalleRepository dalleRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    S3utils s3utils;

    @Override
    public void DalleAnswer(String question) throws Exception {
        URI uri = UriComponentsBuilder.fromUriString(dalleUri).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        DalleReqDTO dalleReqDTO = new DalleReqDTO("dall-e-3", question, 1, "1024x1024", "b64_json");

        HttpEntity<DalleReqDTO> httpEntity = new HttpEntity<>(dalleReqDTO, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<DalleResDTO> response = restTemplate.postForEntity(uri, httpEntity, DalleResDTO.class);
            System.out.println("response = " + response);
            System.out.println("response = " + response);

            Dalle dalle = new Dalle();
            LocalDateTime now = LocalDateTime.now();
            response.getBody().getData().get(0).getRevisedPrompt();

            // 주소로 파일 다운로드
            String b64Json = response.getBody().getData().get(0).getB64Json();

            s3utils.uploadB64ToS3(b64Json, "question");
            // 다운로드된 파일을 s3에 업로드

//
            dalle.setCreated(now);
            dalle.setModified(now);
            dalle.setQuestion(question);
            dalle.setDescription(response.getBody().getData().get(0).getRevisedPrompt());
//            dalle.setUrl(response.getBody().getData().get(0).getUrl());
            dalle.setCreatedNumber(response.getBody().getCreated());

            dalleRepository.save(dalle);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<DalleDTO> getDalleImages() {
        List<Dalle> dalles = dalleRepository.findAll();
        List<DalleDTO> dalleDTOs = new ArrayList<>();
        dalles.stream().forEach(dalle -> {
            DalleDTO dalleDTO = new DalleDTO();
            BeanUtils.copyProperties(dalle, dalleDTO);
            dalleDTOs.add(dalleDTO);
        });
        return dalleDTOs;
    }

    @Override
    public DalleDTO getDalleIamge(String id) {
        Dalle dalle = dalleRepository.findById(id).get();
        DalleDTO dalleDTO = new DalleDTO();

        BeanUtils.copyProperties(dalle, dalleDTO);
        return dalleDTO;
    }
}
