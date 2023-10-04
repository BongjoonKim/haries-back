package com.hariesbackend.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPTResponseDTO {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<ChoicesDTO> choices;
}
