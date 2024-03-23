package com.hariesbackend.contents.repository;

import com.hariesbackend.contents.model.DocumentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends MongoRepository<DocumentsEntity, String> {
    public Page<DocumentsEntity> findAllByFolderId(String folderId, Pageable pageable);
    public Page<DocumentsEntity> findAllByFolderIdIn(List<String> folderIds, Pageable pageable);
    public DocumentsEntity findByUnique(String unique);

}
