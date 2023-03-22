package com.hariesbackend.contents.service.serviceImpl;

import com.hariesbackend.contents.dto.DocumentsDTO;
import com.hariesbackend.contents.model.DocumentsEntity;
import com.hariesbackend.contents.repository.DocumentsRepository;
import com.hariesbackend.contents.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentsServiceImpl implements DocumentsService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DocumentsRepository documentsRepository;

    // 글 데이터 생성
    @Override
    public void createDocuments(DocumentsDTO data) {
        System.out.println("여기보세용");
        DocumentsEntity entity = new DocumentsEntity();
        entity.setTitle(data.getTitles());
        entity.setHtmlContents(data.getHtmlContents());
        documentsRepository.save(entity);
    }


}
