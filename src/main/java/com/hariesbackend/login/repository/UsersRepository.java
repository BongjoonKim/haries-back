package com.hariesbackend.login.repository;

import com.hariesbackend.login.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    public Users findByEmail(String email);
    public Users findByUserId(String UserId);
    public Users findByUserName(String userName);
}
