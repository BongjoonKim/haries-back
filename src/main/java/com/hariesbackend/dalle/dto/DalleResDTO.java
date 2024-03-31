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

    public static class DalleAnswer {
        private String revised_prompt;
        private String url;

        public String getUrl() {
            return this.url;
        }
        public String getRevisedPrompt() {
            return this.revised_prompt;
        }
    }
}
