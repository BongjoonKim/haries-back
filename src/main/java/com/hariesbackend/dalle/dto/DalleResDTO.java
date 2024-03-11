package com.hariesbackend.dalle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DalleResDTO {
    private int created;
    private List<DalleAnswer> data;

    private class DalleAnswer {
        private String revised_prompt;
        private String url;
    }
}
