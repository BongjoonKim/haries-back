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
            GPTMessageDTO messageDTO = new GPTMessageDTO("user", question);
            GPTMessageDTO messageSummarySystem = new GPTMessageDTO("asistant", summarySystemMessage.get(0).getContent());
            GPTMessageDTO messageSummaryUser = new GPTMessageDTO("user", summaryUserMessage.get(0).getContent());

            List<GPTMessageDTO> messageList = new ArrayList<>();

            messageList.add(messageDTO);
            messageList.add(messageSummarySystem);
            messageList.add(messageSummaryUser);

            requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, messageList);

        } else {
            GPTMessageDTO messageDTO = new GPTMessageDTO("user", question);
            requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, Collections.singletonList(messageDTO));
        }
        HttpEntity<GPTRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GPTResponseDTO> response = restTemplate.postForEntity(uri, httpEntity, GPTResponseDTO.class);

        //////////
        GPTMessageDTO newMessageSummaryUser = null;

        // 질문 요약 정리
        if (summarySystemMessage.size() > 0) {

            GPTMessageDTO answerDTO = new GPTMessageDTO("assistant", response.getBody().getChoices().get(0).getMessage().getContent());
            GPTMessageDTO newMessageSummarySystem= newMessageSummarySystem = new GPTMessageDTO("assistant", summarySystemMessage.get(0).getContent()); // 과거 답 요약
            GPTMessageDTO systemSummaryRequest = new GPTMessageDTO("user","대답을 요약해줘"); // 과거 답 요약
            
            List<GPTMessageDTO> summaryAnswerList = new ArrayList<>();
            summaryAnswerList.add(answerDTO);
            summaryAnswerList.add(newMessageSummarySystem);
            summaryAnswerList.add(systemSummaryRequest);

            GPTRequestDTO summaryAnswerDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, summaryAnswerList);
            HttpEntity<GPTRequestDTO> httpEntityOfAnswer = new HttpEntity<>(summaryAnswerDTO, headers);
            ResponseEntity<GPTResponseDTO> responseAnswer = restTemplate.postForEntity(uri, httpEntityOfAnswer, GPTResponseDTO.class);

        } else {    // sumarry가 없을 경우 첫 번째 질문이 summary가 된다

        }

        if (summaryUserMessage.size() > 0) {
            // 응답 요약 정리
            GPTMessageDTO questionDTO = new GPTMessageDTO("user", question); // 과거 답 요약
            newMessageSummaryUser = new GPTMessageDTO("user", summaryUserMessage.get(0).getContent()); // 과거 답 요약
            GPTMessageDTO systemSummaryquestion = new GPTMessageDTO("user","질문을 요약해줘"); // 과거 답 요약

            List<GPTMessageDTO> summaryQuestionList = new ArrayList<>();
            summaryQuestionList.add(newMessageSummaryUser);
            summaryQuestionList.add(questionDTO);
            summaryQuestionList.add(systemSummaryquestion);

            GPTRequestDTO summaryQuestionDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, summaryQuestionList);
            HttpEntity<GPTRequestDTO> httpEntityOfQuestion = new HttpEntity<>(summaryQuestionDTO, headers);

            ResponseEntity<GPTResponseDTO> responseQuestion = restTemplate.postForEntity(uri, httpEntityOfQuestion, GPTResponseDTO.class);
        } else {    // sumarry가 없을 경우 첫 번째 질문이 summary가 된다

        }




        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
