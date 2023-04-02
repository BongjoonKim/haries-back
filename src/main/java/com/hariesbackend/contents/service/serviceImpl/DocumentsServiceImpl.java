package com.hariesbackend.contents.service.serviceImpl;

import com.hariesbackend.contents.dto.DocumentsDTO;
import com.hariesbackend.contents.model.DocumentsEntity;
import com.hariesbackend.contents.repository.DocumentsRepository;
import com.hariesbackend.contents.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentsServiceImpl implements DocumentsService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DocumentsRepository documentsRepository;

    // 글 데이터 생성
    @Override
    public void createDocuments(DocumentsDTO data) {
        // 현재 시스템 시간
        LocalDateTime now = LocalDateTime.now();

        System.out.println("여기보세용");
        DocumentsEntity entity = new DocumentsEntity();
        entity.setTitle(data.getTitles());
        entity.setHtmlContents(data.getHtmlContents());
        entity.setCreated(now);
        documentsRepository.save(entity);
    }

    // 모든 글 데이터 조회
    @Override
    public List<DocumentsDTO> getAllDocuments() {

        List<DocumentsEntity> entityList = documentsRepository.findAll();


        List<DocumentsDTO> documentsDTOList = entityList.stream().map(entity -> {
           return
               new DocumentsDTO(
               entity.getTitle(),
               entity.getHtmlContents(),
               entity.getCreated(),
               entity.getInitialUser()
           );
        }).collect(Collectors.toList());

        // 날짜, 시간 formatting
//        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

        return documentsDTOList;
    }


}
