package com.hariesbackend.dalle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DalleReqDTO {
    private String model;
    private String prompt;
    private int n;
    private String size;
    private String response_format;
}
