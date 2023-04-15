package com.hariesbackend.contents.service;

import com.hariesbackend.contents.dto.DocumentInfo;
import com.hariesbackend.contents.dto.PaginationDTO;

import java.util.List;

public interface DocumentsService {

    // 글 데이터 생성
    void createDocuments(DocumentInfo.DocumentsDTO documentsDTO);

    // 모든글 데이터 조회
    DocumentInfo getAllDocuments(PaginationDTO paginationDTO);

}
