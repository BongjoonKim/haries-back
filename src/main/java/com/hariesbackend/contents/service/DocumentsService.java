package com.hariesbackend.contents.service;

import com.hariesbackend.contents.dto.DocumentsInfo;
import com.hariesbackend.contents.dto.PaginationDTO;

import java.util.List;

public interface DocumentsService {

    // 글 데이터 생성
    DocumentsInfo.DocumentDTO createDocuments(DocumentsInfo.DocumentDTO documentsDTO);

    // 모든글 데이터 조회
    DocumentsInfo getAllDocuments(PaginationDTO paginationDTO);

    // 특정 글 조회
    DocumentsInfo.DocumentDTO getDocument(String id);

    // unique로 조회
    DocumentsInfo.DocumentDTO getDocumentUnique(String unique);

    void deleteDocument(String id);

    void saveDocument(String id, DocumentsInfo.DocumentDTO data);

}
