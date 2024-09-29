package com.hariesbackend.chatting.repository;

import com.hariesbackend.chatting.model.Channels;
import com.hariesbackend.chatting.model.MessagesHistory;
import com.hariesbackend.contents.model.DocumentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageHistoryRepository extends MongoRepository<MessagesHistory, String> {
    Page<MessagesHistory> findByChannelId(String channelId, Pageable pageable);

    List<MessagesHistory> findByChannelIdOrderByCreatedDesc(String channelId);
    List<MessagesHistory> findByChannelIdAndUserId(String channelId, String userId);

    Page<MessagesHistory> findByChannelIdAndUserIdNotIn(String channelId, List<String> userIds, Pageable pageable);
    void deleteAllByChannelId(String channelId);

    int countByChannelId(String channelId);

    int countByChannelIdAndUserIdNotIn(String channelId, List<String> channelIds);

    List<MessagesHistory> findByUserId(String userId);
}
