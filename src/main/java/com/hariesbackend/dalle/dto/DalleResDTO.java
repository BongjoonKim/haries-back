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

    @Data
    public static class DalleAnswer {
        private String b64_json;
        private String revised_prompt;

        public String getRevisedPrompt() {
            return revised_prompt;
        }
        public String getB64Json() {return b64_json;}
    }
}
