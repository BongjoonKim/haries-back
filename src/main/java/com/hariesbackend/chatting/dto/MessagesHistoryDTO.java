package com.hariesbackend.chatting.dto;

import com.hariesbackend.chatting.model.MessagesHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MessagesHistoryDTO extends MessagesHistory {
    private String bot;

    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }
}
