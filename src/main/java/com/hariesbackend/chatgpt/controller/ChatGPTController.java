package com.hariesbackend.chatgpt.controller;

import com.hariesbackend.chatgpt.service.GPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatgpt")
@Slf4j
public class ChatGPTController {
    @Autowired
    private GPTService gptService;

    @PostMapping("/open-ai")
    public String getGPTAnswer(@RequestBody String question) {
        return gptService.gptAnswer(question);
    }
}
