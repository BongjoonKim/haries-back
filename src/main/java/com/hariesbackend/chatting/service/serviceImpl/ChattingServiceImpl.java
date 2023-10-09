package com.hariesbackend.chatting.service.serviceImpl;

import com.hariesbackend.chatting.constants.AdminConstant;
import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Authority;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.chatting.repository.ChannelRepository;
import com.hariesbackend.chatting.repository.MessageHistoryRepository;
import com.hariesbackend.chatting.service.ChattingService;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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
            channels.setDetail("");
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
        } else {
            user = usersRepository.findByUserName("김봉준");
        }


        messagesHistory.setChannelId(channelId);
        messagesHistory.setContent(content);
        messagesHistory.setUserId(user.getUserId());
        messagesHistory.setCreated(now);

        messageHistoryRepository.save(messagesHistory);
    }

    // 채넘 정보 획득
    @Override
    public ChannelDTO getChannel(String channelId) throws Exception {
        ChannelDTO channelDTO = new ChannelDTO();

        BeanUtils.copyProperties(channelRepository.findById(channelId).get(), channelDTO);


        return channelDTO;
    }

    @Override
    public List<ChannelDTO> getChannels() throws Exception {
        try {
            List<ChannelDTO> channelDTOList = new ArrayList<>();
            List<Channels> channels = channelRepository.findAll();
            Users user = usersRepository.findByUserName("김봉준");

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
    public List<MessagesHistoryDTO> getMessages(String channelId) throws Exception {
        List<MessagesHistory> messagesHistories = messageHistoryRepository.findByChannelId(channelId);
        List<MessagesHistoryDTO> messagesHistoryDTOList = new ArrayList<>();

        messagesHistories.stream().forEach(el -> {
            MessagesHistoryDTO messageDTO = new MessagesHistoryDTO();
            BeanUtils.copyProperties(el, messageDTO);
            messagesHistoryDTOList.add(messageDTO);
        });

        return messagesHistoryDTOList;
    }
}
