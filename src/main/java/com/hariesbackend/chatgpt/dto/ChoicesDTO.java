package com.hariesbackend.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChoicesDTO {
    private int index;
    private GPTMessageDTO message;
}
