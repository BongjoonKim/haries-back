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
        private String title;
        private String contents;
        private String contentsType;
        private boolean disclose;
        private List<String> tags;
        private LocalDateTime created;
        private LocalDateTime modified;
        private String initialUser;
        private String modifiedUser;
        private String unique;
        private String folderId;
        private String color;
    }


}
