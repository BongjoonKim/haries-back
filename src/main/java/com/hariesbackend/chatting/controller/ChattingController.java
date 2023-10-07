package com.hariesbackend.chatting.controller;

import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.chatting.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatting")
@Slf4j
public class ChattingController {
    @Autowired
    private ChattingService chattingService;

    // 채널 생성
    @PostMapping("/channel")
    public void createChannel(@RequestBody String channelName) {
        try {
            chattingService.createChannel(channelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 메세지 생성
    @PostMapping("/message")
    public void createMessage(@RequestBody MessagesHistory message) {
        try {
            chattingService.createMessage(message.getChannelId(), message.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("channel")
    public ChannelDTO getChannel(@RequestParam("channelId") String channelId) {
        try {
            return chattingService.getChannel(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("messages")
    public List<MessagesHistoryDTO> getMessages(@RequestParam("channelId") String channelId) {
        try {
            return chattingService.getMessages(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
