package com.hariesbackend.contents.service;

import com.hariesbackend.contents.dto.DocumentsInfo;
import com.hariesbackend.contents.dto.PaginationDTO;

import java.util.List;

public interface DocumentsService {

    // 글 데이터 생성
    void createDocuments(DocumentsInfo.DocumentDTO documentsDTO);

    // 모든글 데이터 조회
    DocumentsInfo getAllDocuments(PaginationDTO paginationDTO);

    DocumentsInfo.DocumentDTO getDocument(String id);

    void deleteDocument(String id);

}
