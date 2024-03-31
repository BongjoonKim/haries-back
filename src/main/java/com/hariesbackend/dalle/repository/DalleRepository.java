package com.hariesbackend.dalle.repository;

import com.hariesbackend.dalle.model.Dalle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DalleRepository extends MongoRepository<Dalle, String> {
    List<Dalle> findByUserId(String userId);
}
