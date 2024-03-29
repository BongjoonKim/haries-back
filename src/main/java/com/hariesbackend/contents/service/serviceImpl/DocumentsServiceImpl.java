package com.hariesbackend.contents.service.serviceImpl;

import com.hariesbackend.contents.dto.DocumentsInfo;
import com.hariesbackend.contents.dto.PaginationDTO;
import com.hariesbackend.contents.model.DocumentsEntity;
import com.hariesbackend.contents.repository.DocumentsRepository;
import com.hariesbackend.contents.service.DocumentsService;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.repository.FoldersRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.util.NullableUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Autowired
    FoldersRepository foldersRepository;

    // 글 데이터 생성
    @Override
    public DocumentsInfo.DocumentDTO createDocuments(DocumentsInfo.DocumentDTO data) {
        DocumentsEntity entity = new DocumentsEntity();
        if (data.getTitle().isEmpty()) {
            // 현재 시스템 시간
            LocalDateTime now = LocalDateTime.now();
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
            entity.setColor(data.getColor());
        }
        DocumentsInfo.DocumentDTO documentDTO = new DocumentsInfo.DocumentDTO();
        DocumentsEntity documentsEntity = documentsRepository.save(entity);
        BeanUtils.copyProperties(documentsEntity, documentDTO);
        return documentDTO;
    }

    // 모든 글 데이터 조회
    @Override
    public DocumentsInfo getAllDocuments(PaginationDTO paginationDTO) {

        Page<DocumentsEntity> entityPage = null;
        FoldersEntity selectedFolder = null;

        if ("all".equals(paginationDTO.getFolderId())) {
            entityPage = documentsRepository.findAll(PageRequest.of(paginationDTO.getPage() - 1 ,paginationDTO.getSize()));
        } else {
            // 1. 폴더 아이디를 통해 folder path를 구한다
            selectedFolder = foldersRepository.findById(paginationDTO.getFolderId()).get();

            // 2. 해당 folder path를 가지는 폴더 id 목록을 가져온다 ( 선택한 폴더의 하위 모든 폴더 가져오기 )
            List<FoldersEntity> getAllSubFolders = foldersRepository.findAllByPathContains(selectedFolder.getPath());

            // 3. 선택한 폴더와 하위 모든 폴더 ID 가져오기
            List<String> getAllSubFolderIds = getAllSubFolders.stream().map(subFolder -> subFolder.getId()
            ).collect(Collectors.toList());

//            entityPage = documentsRepository.findAllByFolderId(paginationDTO.getFolderId(), PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getSize()));
            entityPage = documentsRepository.findAllByFolderIdIn(getAllSubFolderIds, PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getSize()));
        }

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
            entity.getFolderId(),
            entity.getColor()
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

        entity.setTitle(data.getTitle());
        entity.setContents(data.getContents());
        entity.setContentsType(data.getContentsType());
        entity.setDisclose(data.isDisclose());
        entity.setTags(data.getTags());
        entity.setModified(now);
        entity.setModifiedUser("haries");
        entity.setUnique(data.getUnique());
        entity.setFolderId(data.getFolderId());
        entity.setColor(data.getColor());


        documentsRepository.save(entity);
    }
}
