package com.hariesbackend.chatting.repository;

import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.contents.model.DocumentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageHistoryRepository extends MongoRepository<MessagesHistory, String> {
    List<MessagesHistory> findByChannelId(String channelId);

}
