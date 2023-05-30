package com.hariesbackend.folders.repository;

import com.hariesbackend.folders.model.FoldersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoldersRepository extends MongoRepository<FoldersEntity, String> {
    public List<FoldersEntity> findByDepth(int depth);
}
