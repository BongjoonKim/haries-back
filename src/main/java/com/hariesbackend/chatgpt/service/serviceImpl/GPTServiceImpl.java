package com.hariesbackend.chatgpt.service.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.chatgpt.dto.GPTMessageDTO;
import com.hariesbackend.chatgpt.dto.GPTRequestDTO;
import com.hariesbackend.chatgpt.dto.GPTResponseDTO;
import com.hariesbackend.chatgpt.service.GPTService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService {

    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.uri}")
    private String gptURI;

    @Override
    public String gptAnswer(String question) {
        URI uri = UriComponentsBuilder.fromUriString(gptURI).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        GPTMessageDTO messageDTO = new GPTMessageDTO("user", question);
        GPTRequestDTO requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, Collections.singletonList(messageDTO));

        HttpEntity<GPTRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GPTResponseDTO> response = restTemplate.postForEntity(uri, httpEntity, GPTResponseDTO.class);

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
