package com.hariesbackend.login.repository;

import com.hariesbackend.contents.model.DocumentsEntity;
import com.hariesbackend.login.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
}
