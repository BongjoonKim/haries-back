package com.hariesbackend.dalle.service.serviceImpl;

import com.hariesbackend.dalle.dto.DalleResDTO;
import com.hariesbackend.dalle.service.DalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleServiceImpl implements DalleService {
    @Value("${spring.gpt.api-key}")
    private String token;

    @Value("${spring.gpt.dalle-uri}")
    private String dalleUri;



    @Override
    public DalleResDTO DalleAnswer(String channelId, String question) {
        return null;
    }
}
