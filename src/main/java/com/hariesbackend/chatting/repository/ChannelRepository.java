package com.hariesbackend.chatting.repository;

import com.hariesbackend.chatting.model.Channels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends MongoRepository<Channels, String> {
}