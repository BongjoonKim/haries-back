package com.hariesbackend.contents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentsDTO {

    public DocumentsDTO() {

    }
    private String titles;
    private String htmlContents;
    private LocalDateTime created;
    private String initialUser;
}
