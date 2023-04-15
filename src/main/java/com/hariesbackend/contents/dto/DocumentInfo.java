package com.hariesbackend.contents.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo {
    private long totalContents;
    private int totalPages;
    private List<DocumentsDTO> documentsDTO;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocumentsDTO {
        private String id;
        private String titles;
        private String htmlContents;
        private LocalDateTime created;
        private String initialUser;
    }


}
