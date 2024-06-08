package com.hariesbackend.chatgpt.service.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariesbackend.chatgpt.dto.GPTMessageDTO;
import com.hariesbackend.chatgpt.dto.GPTRequestDTO;
import com.hariesbackend.chatgpt.dto.GPTResponseDTO;
import com.hariesbackend.chatgpt.service.GPTService;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.chatting.repository.MessageHistoryRepository;
import com.hariesbackend.login.repository.UsersRepository;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService {

    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.uri}")
    private String gptURI;

    @Value("${spring.gpt.version}")
    private String gptVersion;

    @Value("${spring.gpt.temperature}")
    private double gptTemperature;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MessageHistoryRepository messageHistoryRepository;

    @Override
    public String gptAnswer(String channelId, String question) {
        URI uri = UriComponentsBuilder.fromUriString(gptURI).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        GPTRequestDTO requestDTO = null;


        List<MessagesHistory> summarySystemMessage = messageHistoryRepository.findByChannelIdAndUserId(channelId, "SummarySystem");
        List<MessagesHistory> summaryUserMessage = messageHistoryRepository.findByChannelIdAndUserId(channelId, "SummaryUser");

        if (summarySystemMessage.size() > 0 || summaryUserMessage.size() > 0) {
            List<GPTMessageDTO> messageList = new ArrayList<>();
            GPTMessageDTO messageDTO = new GPTMessageDTO("user", question);
            messageList.add(messageDTO);

            if (summarySystemMessage.size() > 0) {
                GPTMessageDTO messageSummarySystem = new GPTMessageDTO("assistant", summarySystemMessage.get(0).getContent());
                messageList.add(messageSummarySystem);
            }
            if (summaryUserMessage.size() > 0) {
                GPTMessageDTO messageSummaryUser = new GPTMessageDTO("user", summaryUserMessage.get(0).getContent());
                messageList.add(messageSummaryUser);
            }

            requestDTO = new GPTRequestDTO(gptVersion, gptTemperature, false, messageList);

        } else {
            GPTMessageDTO messageDTO = new GPTMessageDTO("user", question);
            requestDTO = new GPTRequestDTO(gptVersion, gptTemperature, false, Collections.singletonList(messageDTO));
        }

        HttpEntity<GPTRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GPTResponseDTO> response = restTemplate.postForEntity(uri, httpEntity, GPTResponseDTO.class);

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
