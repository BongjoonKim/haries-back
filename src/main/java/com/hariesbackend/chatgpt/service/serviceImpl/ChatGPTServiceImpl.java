package com.hariesbackend.chatgpt.service.serviceImpl;

import com.hariesbackend.chatgpt.service.ChatGPTService;
import com.hariesbackend.contents.repository.DocumentsRepository;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${spring.gpt.token}")
    private String chatGPTToken;

    @Bean
    OpenAiService openAiService;

    @Override
    public Object RequestAnswer(String question) {

        return null;
    }
}
