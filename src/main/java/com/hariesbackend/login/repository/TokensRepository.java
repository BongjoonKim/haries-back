package com.hariesbackend.login.repository;

import com.hariesbackend.login.model.Tokens;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends MongoRepository<Tokens, String> {
    Tokens findByAccessToken(String accessToken);
    Tokens findByRefreshToken(String refreshToken);
    Tokens findByEmail(String email);

}
