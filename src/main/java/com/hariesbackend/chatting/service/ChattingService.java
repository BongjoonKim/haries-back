package com.hariesbackend.chatting.service;

import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;

import java.util.List;

public interface ChattingService {
    public void createChannel(String name) throws Exception;
    public void createMessage(String channelId, String content) throws Exception;
    public ChannelDTO getChannel(String channelId) throws Exception;
    public List<ChannelDTO> getChannels() throws Exception;
    public void deleteChannel(String channelId) throws Exception;
    public List<MessagesHistoryDTO> getMessages(String channelId) throws Exception;
}
