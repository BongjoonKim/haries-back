package com.hariesbackend.chatting.dto;

import com.hariesbackend.chatting.model.Channels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class ChannelDTO extends Channels {
    private String lastestMessage;

    public String getLastestMessage() {
        return lastestMessage;
    }

    public void setLastestMessage(String lastestMessage) {
        this.lastestMessage = lastestMessage;
    }
}
