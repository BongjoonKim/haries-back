package com.hariesbackend.dalle.model;

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
public class Dalle {
    @Id
    private String id;
    private String url;
    private String savedUrl;
    private String title;
    private String question;
    private String userId;
    private String description;
    private LocalDateTime created;
    private int createdNumber;
    private LocalDateTime modified;
}
