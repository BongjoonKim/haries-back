package com.hariesbackend.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPTRequestDTO {
    private String model;
    private Double temperature;
    private Boolean stream;
    private List<GPTMessageDTO> messages;
}
