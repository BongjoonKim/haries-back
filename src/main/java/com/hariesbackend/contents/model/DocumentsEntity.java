package com.hariesbackend.contents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentsEntity {
    @Id
    private String id;
    private String title;
    private String htmlContents;
    private boolean disclose;
    private String[] tags;
}
