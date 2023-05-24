package com.hariesbackend.contents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentsEntity {
    @Id
    private String id;
    private String title;
    private String contents;
    private String contentsType;
    private boolean disclose;
    private String[] tags;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String initialUser;
    private String modifiedUser;
    private String unique;
}
