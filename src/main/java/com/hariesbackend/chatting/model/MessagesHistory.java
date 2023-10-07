package com.hariesbackend.chatting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagesHistory {
    @Id
    private String id;
    private String channelId;
    private String content;
    private String userId;
    private LocalDateTime created;
}
