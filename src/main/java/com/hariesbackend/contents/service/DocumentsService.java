package com.hariesbackend.contents.service;

import com.hariesbackend.contents.dto.DocumentsDTO;

import java.util.List;

public interface DocumentsService {

    // 글 데이터 생성
    void createDocuments(DocumentsDTO data);

    // 모든글 데이터 조회
    List<DocumentsDTO> getAllDocuments();

}
