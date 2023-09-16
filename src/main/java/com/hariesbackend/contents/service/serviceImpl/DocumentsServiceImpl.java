package com.hariesbackend.contents.service.serviceImpl;

import com.hariesbackend.contents.dto.DocumentsInfo;
import com.hariesbackend.contents.dto.PaginationDTO;
import com.hariesbackend.contents.model.DocumentsEntity;
import com.hariesbackend.contents.repository.DocumentsRepository;
import com.hariesbackend.contents.service.DocumentsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocumentsServiceImpl implements DocumentsService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DocumentsRepository documentsRepository;

    // 글 데이터 생성
    @Override
    public void createDocuments(DocumentsInfo.DocumentDTO data) {
        // 현재 시스템 시간
        LocalDateTime now = LocalDateTime.now();

        DocumentsEntity entity = new DocumentsEntity();
        entity.setTitle(data.getTitle());
        entity.setContents(data.getContents());
        entity.setContentsType(data.getContentsType());
        entity.setDisclose(data.isDisclose());
        entity.setTags(data.getTags());
        entity.setCreated(now);
        entity.setModified(now);
        entity.setInitialUser("haries");
        entity.setModifiedUser("haries");
        entity.setUnique(data.getUnique());
        entity.setFolderId(data.getFolderId());
//        BeanUtils.copyProperties(data, entity);

        documentsRepository.save(entity);
    }

    // 모든 글 데이터 조회
    @Override
    public DocumentsInfo getAllDocuments(PaginationDTO paginationDTO) {

        Page<DocumentsEntity> entityPage = documentsRepository.findAll(PageRequest.of(paginationDTO.getPage() - 1 ,paginationDTO.getSize()));

        List<DocumentsInfo.DocumentDTO> documentsDTOList = entityPage.getContent().stream().map(entity -> new DocumentsInfo.DocumentDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getContents(),
            entity.getContentsType(),
            entity.isDisclose(),
            entity.getTags(),
            entity.getCreated(),
            entity.getModified(),
            entity.getInitialUser(),
            entity.getModifiedUser(),
            entity.getUnique(),
            entity.getFolderId()
        )).collect(Collectors.toList());


        DocumentsInfo documentInfo = new DocumentsInfo();
        documentInfo.setDocumentsDTO(documentsDTOList);
        documentInfo.setTotalPages(entityPage.getTotalPages());
        documentInfo.setTotalContents(entityPage.getTotalElements());

        // 날짜, 시간 formatting
//        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        return documentInfo;
    }

    // 특정 글 조회
    @Override
    public DocumentsInfo.DocumentDTO getDocument(String id) {
        DocumentsEntity entity = documentsRepository.findById(id).get();
        DocumentsInfo.DocumentDTO document = new DocumentsInfo.DocumentDTO();
//        document.setId(entity.getId());
//        document.setTitles(entity.getTitle());
//        document.setContents(entity.getContents());
//        document.setContentsType(entity.getContentsType());
//        document.setTags(entity.getTags());
//        document.setCreated(entity.getCreated());
//        document.setModified(entity.getModified());
//        document.setModifiedUser(entity.getModifiedUser());
//        document.setUnique(entity.getUnique());
        BeanUtils.copyProperties(entity, document);
        return document;
    }

    @Override
    public DocumentsInfo.DocumentDTO getDocumentUnique(String unique) {
        DocumentsEntity entity = documentsRepository.findByUnique(unique);
        DocumentsInfo.DocumentDTO document = new DocumentsInfo.DocumentDTO();
//        document.setId(entity.getId());
//        document.setTitles(entity.getTitle());
//        document.setContents(entity.getContents());
//        document.setContentsType(entity.getContentsType());
//        document.setCreated(entity.getCreated());
//        document.setModified(entity.getModified());
//        document.setModifiedUser(entity.getModifiedUser());
//        document.setUnique(entity.getUnique());
        BeanUtils.copyProperties(entity, document);
        return document;
    }

    // 글 삭제
    @Override
    public void deleteDocument(String id) {
        documentsRepository.deleteById(id);
    }

    // 글 수정
    @Override
    public void saveDocument(String id, DocumentsInfo.DocumentDTO data) {
        // 현재 시스템 시간
        LocalDateTime now = LocalDateTime.now();
        DocumentsEntity entity = documentsRepository.findById(id).get();

//        DocumentsEntity entity = new DocumentsEntity();
        entity.setTitle(data.getTitle());
        entity.setContents(data.getContents());
        entity.setContentsType(data.getContentsType());
        entity.setDisclose(data.isDisclose());
        entity.setTags(data.getTags());
        entity.setModified(now);
        entity.setModifiedUser("haries");
        entity.setUnique(data.getUnique());
        entity.setFolderId(data.getFolderId());


        documentsRepository.save(entity);
    }
}
