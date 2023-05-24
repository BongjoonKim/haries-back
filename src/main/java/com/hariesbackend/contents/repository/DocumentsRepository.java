package com.hariesbackend.contents.repository;

import com.hariesbackend.contents.model.DocumentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends MongoRepository<DocumentsEntity, String> {
    public DocumentsEntity findByUnique(String unique);
}
