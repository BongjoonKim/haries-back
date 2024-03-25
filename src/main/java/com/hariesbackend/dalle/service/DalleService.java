package com.hariesbackend.dalle.service;

import com.hariesbackend.dalle.dto.DalleResDTO;

import java.util.List;

public interface DalleService {
    DalleResDTO DalleAnswer(String question);

    List<DalleResDTO> getDalleImages();

    DalleResDTO getDalleIamge(String id);
}
