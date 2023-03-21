package com.hariesbackend.contents.service.serviceImpl;

import com.hariesbackend.contents.model.Documents;
import com.hariesbackend.contents.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentsServiceImpl implements DocumentsService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void createDocuments(Documents data) {

    }
}
