package com.hariesbackend.chatting.service.serviceImpl;

import com.hariesbackend.chatgpt.dto.GPTMessageDTO;
import com.hariesbackend.chatgpt.dto.GPTRequestDTO;
import com.hariesbackend.chatgpt.dto.GPTResponseDTO;
import com.hariesbackend.chatting.constants.AdminConstant;
import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagePaginationDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Authority;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.chatting.repository.ChannelRepository;
import com.hariesbackend.chatting.repository.MessageHistoryRepository;
import com.hariesbackend.chatting.service.ChattingService;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import com.hariesbackend.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChattingServiceImpl implements ChattingService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MessageHistoryRepository messageHistoryRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LoginService loginService;

    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.uri}")
    private String gptURI;

    // 채널 생성
    @Override
    public void createChannel(String name) throws Exception {
        try {
            Channels channels = new Channels();

            // TODO 스프링 시큐리티 적용 후 작업 필요
            Users user = usersRepository.findByUserName("김봉준");
            List<Authority> authorities = new ArrayList<>();
            Authority authority = new Authority();
            authority.setAuth(AdminConstant.ADMIN.get());
            authority.setUserId(user.getUserId());
            authorities.add(authority);

            LocalDateTime now = LocalDateTime.now();

            channels.setAuthorities(authorities);
            channels.setName(name);
            channels.setCreated(now);
            channels.setModified(now);

            channelRepository.save(channels);
        } catch (Exception e){
            throw e;
        }

    }

    // 메세지 하나 입력할 때
    @Override
    public void createMessage(String channelId, String content, String bot) throws Exception {
        MessagesHistory messagesHistory = new MessagesHistory();
        LocalDateTime now = LocalDateTime.now();
        Users user = null;
        if (bot.equals("ChatGPT")) {
            user = usersRepository.findByUserName("ChatGPT");
            if (ObjectUtils.isEmpty(user)) {
                Users User = new Users();
                // ChatGPT 봇 생성
                User.setActive(true);
                User.setAge(0);
                User.setAgeRange("0");
                User.setBot(true);
                User.setCreated(now);
                User.setModified(now);
                User.setNickname("ChatGPT");
                User.setRoles(new ArrayList<>());
                User.setUserId("ChatGPT");
                User.setUserName("ChatGPT");
                User.setUserPassword(loginService.getRamdomPassword(20));

                user = usersRepository.findByUserName("ChatGPT");
            }
        } else {
            user = usersRepository.findByUserName("김봉준");
        }

        messagesHistory.setChannelId(channelId);
        messagesHistory.setContent(content);
        messagesHistory.setUserId(user.getUserId());
        messagesHistory.setCreated(now);

        messageHistoryRepository.save(messagesHistory);


        // 추가 질문 및 저장
        URI uri = UriComponentsBuilder.fromUriString(gptURI).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        if (bot.equals("ChatGPT")) {
            // 보낼 메세지를 담을 변수
            List<GPTMessageDTO> messageList = new ArrayList<>();
            GPTRequestDTO requestDTO = null;
            GPTMessageDTO askAnswer = new GPTMessageDTO("user", "요약해줘");

            // 현재 답변
            GPTMessageDTO thisAnswer = new GPTMessageDTO("assistant", content);
            // 과거 답변
            List<MessagesHistory> summarySystemMessage = messageHistoryRepository.findByChannelIdAndUserId(channelId, "SummarySystem");

            // 과거 답변이 존재하는 경우
            if (summarySystemMessage.size() > 0) {
                GPTMessageDTO pastAnswer = new GPTMessageDTO("assistant", summarySystemMessage.get(0).getContent());
                messageList.add(thisAnswer);
                messageList.add(pastAnswer);
                messageList.add(askAnswer);
                requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, messageList);
            } else {
                messageList.add(thisAnswer);
                messageList.add(askAnswer);
                requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, messageList);
            }
            HttpEntity<GPTRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GPTResponseDTO> response = restTemplate.postForEntity(uri, httpEntity, GPTResponseDTO.class);

            // DB에 저장
            // 기존에 요약본이 있을 경우
            if (summarySystemMessage.size() > 0) {
                summarySystemMessage.get(0).setContent(response.getBody().getChoices().get(0).getMessage().getContent());
                messageHistoryRepository.save(summarySystemMessage.get(0));
            } else {    // 기존의 요약본이 없을 경우
                MessagesHistory messageSummary = new MessagesHistory();
                messageSummary.setChannelId(channelId);
                messageSummary.setUserId("SummarySystem");
                messageSummary.setContent(response.getBody().getChoices().get(0).getMessage().getContent());
                messageSummary.setCreated(now);
                messageHistoryRepository.save(messageSummary);
            }
        // 질문한 경우 요약
        } else {
            List<GPTMessageDTO> messageList = new ArrayList<>();
            GPTRequestDTO requestDTO = null;
            GPTMessageDTO askAnswer = new GPTMessageDTO("user", "요약해줘");

            // 현재 질문
            GPTMessageDTO thisQuestion = new GPTMessageDTO("assistant", content);

            // 과거 질문
            List<MessagesHistory> summaryUserMessage = messageHistoryRepository.findByChannelIdAndUserId(channelId, "SummaryUser");

            // 과거 질문이 존재하는 경우
            if (summaryUserMessage.size() > 0) {
                GPTMessageDTO pastQuestion = new GPTMessageDTO("user", summaryUserMessage.get(0).getContent());
                messageList.add(thisQuestion);
                messageList.add(pastQuestion);
                messageList.add(askAnswer);
                requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, messageList);
            } else {
                messageList.add(thisQuestion);
                messageList.add(askAnswer);
                requestDTO = new GPTRequestDTO("gpt-4-1106-preview", 1.0, false, messageList);
            }

            HttpEntity<GPTRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<GPTResponseDTO> response = restTemplate.postForEntity(uri, httpEntity, GPTResponseDTO.class);

            // DB에 저장
            // 기존에 요약본이 있을 경우
            if (summaryUserMessage.size() > 0) {
                summaryUserMessage.get(0).setContent(response.getBody().getChoices().get(0).getMessage().getContent());
                messageHistoryRepository.save(summaryUserMessage.get(0));
            } else {    // 기존의 요약본이 없을 경우
                MessagesHistory messageSummary = new MessagesHistory();
                messageSummary.setChannelId(channelId);
                messageSummary.setUserId("SummaryUser");
                messageSummary.setContent(response.getBody().getChoices().get(0).getMessage().getContent());
                messageSummary.setCreated(now);
                messageHistoryRepository.save(messageSummary);
            }
        }
    }

    // 채넘 정보 획득
    @Override
    public ChannelDTO getChannel(String channelId) throws Exception {
        ChannelDTO channelDTO = new ChannelDTO();

        BeanUtils.copyProperties(channelRepository.findById(channelId).get(), channelDTO);


        return channelDTO;
    }

    @Override
    public List<ChannelDTO> getChannels(String channelName) throws Exception {
        try {
            List<ChannelDTO> channelDTOList = new ArrayList<>();
            List<Channels> channels;
            Users user = usersRepository.findByUserName("김봉준");

            if (StringUtils.isEmpty(channelName)) {
                channels = channelRepository.findAll();
            } else {
                channels= channelRepository.findByNameContaining(channelName);
            }

            channels.stream().forEach(el -> {
                boolean haveUser = false;
                for(Authority authority : el.getAuthorities()) {
                    if(user.getUserId().equals(authority.getUserId())) {
                        haveUser = true;
                        break;
                    }
                }
                if (haveUser) {
                    ChannelDTO channelDTO = new ChannelDTO();
                    BeanUtils.copyProperties(el, channelDTO);

                    // 이 채널의 마지막 대화내용 보이기
                    List<MessagesHistory> lastestMessage = messageHistoryRepository.findByChannelIdOrderByCreatedDesc(el.getId());
                    if (lastestMessage.size() != 0) {
                        channelDTO.setLastestMessage(lastestMessage.get(0).getContent());
                    } else {
                        channelDTO.setLastestMessage("");
                    }
                    channelDTOList.add(channelDTO);
                }
            });

            return channelDTOList;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteChannel(String channelId) throws Exception {
        try {
            channelRepository.deleteById(channelId);
            messageHistoryRepository.deleteAllByChannelId(channelId);
        } catch (Exception e) {
            throw e;
        }
    }

    // 메세지 대화 내용 가져오기
    @Override
    public MessagePaginationDTO getMessages(String channelId, int page) throws Exception {
        Page<MessagesHistory> messagesHistories;
        int pageSize = 15;
        int nextPageNumber = -1;
        if (page == -1) {   // 마지막 페이지를 구하라는 느낌
            int allMessagesCnt = messageHistoryRepository.countByChannelId(channelId);
            int lastPageNumber = 0;
            if (allMessagesCnt != 0) {
                lastPageNumber = Math.floorDiv(allMessagesCnt-1, pageSize);
            }
            messagesHistories = messageHistoryRepository.findByChannelId(
                    channelId,
                    PageRequest.of(lastPageNumber, 15)
            );
            nextPageNumber = lastPageNumber - 1;
        } else {
            messagesHistories = messageHistoryRepository.findByChannelId(channelId, PageRequest.of(page, pageSize));
            nextPageNumber = page - 1;
        }


        List<MessagesHistoryDTO> messagesHistoryDTOList = new ArrayList<>();

        messagesHistories.stream().forEach(el -> {
            MessagesHistoryDTO messageDTO = new MessagesHistoryDTO();
            BeanUtils.copyProperties(el, messageDTO);
            messagesHistoryDTOList.add(messageDTO);
        });

        MessagePaginationDTO messagePaginationDTO = new MessagePaginationDTO();
        messagePaginationDTO.setMessagesHistory(messagesHistoryDTOList);
        messagePaginationDTO.setNextPage(nextPageNumber);

        return messagePaginationDTO;
    }
}
