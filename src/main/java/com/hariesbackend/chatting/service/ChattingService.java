package com.hariesbackend.chatting.service;

import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagePaginationDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    public ChannelDTO createChannel(String name) throws Exception;
    public void createUserMessage(String channelId, String content, String bot) throws Exception;
    public void createMessage(String channelId, String content, String bot) throws Exception;
    public ChannelDTO getChannel(String channelId) throws Exception;
    public List<ChannelDTO> getChannels(String channelName, String message) throws Exception;
    public void deleteChannel(String channelId) throws Exception;
    public MessagePaginationDTO getMessages(String channelId, int page) throws Exception;
}
