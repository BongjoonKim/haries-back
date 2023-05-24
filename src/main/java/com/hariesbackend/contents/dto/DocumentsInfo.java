package com.hariesbackend.contents.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsInfo {
    private long totalContents;
    private int totalPages;
    private List<DocumentDTO> documentsDTO;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocumentDTO {
        private String id;
        private String titles;
        private String contents;
        private String contentsType;
        private LocalDateTime created;
        private String initialUser;
        private LocalDateTime modified;
        private String modifiedUser;
        private String unique;
    }


}
