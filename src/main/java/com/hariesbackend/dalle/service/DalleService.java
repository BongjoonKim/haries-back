package com.hariesbackend.dalle.service;

import com.hariesbackend.dalle.dto.DalleResDTO;

public interface DalleService {
    DalleResDTO DalleAnswer(String channelId, String question);

}
