package com.hariesbackend.chatting.controller;

import com.hariesbackend.chatting.dto.ChannelDTO;
import com.hariesbackend.chatting.dto.MessagePaginationDTO;
import com.hariesbackend.chatting.dto.MessagesHistoryDTO;
import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.chatting.service.ChattingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatting")
@Slf4j
public class ChattingController {
    // 윈도우 푸쉬 테스트
    @Autowired
    private ChattingService chattingService;

    // 채널 생성
    @PostMapping("/channel")
    public ChannelDTO createChannel(@RequestBody Map<String, String> channelName) {
        try {
            return chattingService.createChannel(channelName.get("channelName"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.valueOf("error"), e.getMessage());
        }
    }

    // 사용자 메세지 생성
    @PostMapping("/user/message")
    public void createUserMessage(@RequestBody MessagesHistoryDTO message) {
        try {
            chattingService.createUserMessage(message.getChannelId(), message.getContent(), message.getBot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 메세지 생성
    @PostMapping("/message")
    public void createMessage(@RequestBody MessagesHistoryDTO message) {
        try {
            chattingService.createMessage(message.getChannelId(), message.getContent(), message.getBot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/channel")
    public ChannelDTO getChannel(@RequestParam("channelId") String channelId) {
        try {
            return chattingService.getChannel(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/channels")
    public List<ChannelDTO> getChannels(
            @RequestParam("channelName") String channelName,
            @RequestParam("message") String message
    ) {
        try {

            return chattingService.getChannels(channelName, message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/channel")
    public void deleteChannel(@RequestParam("channelId") String channelId) {
        try {
            chattingService.deleteChannel(channelId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/messages")
    public MessagePaginationDTO getMessages(
            @RequestParam("channelId") String channelId,
            @RequestParam("page") int page
    ) {
        try {
            return chattingService.getMessages(channelId, page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
