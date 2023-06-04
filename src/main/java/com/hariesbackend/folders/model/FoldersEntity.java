package com.hariesbackend.folders.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoldersEntity {
    @Id
    private String id;
    private String uniqueKey;
    private String label;
    private int depth;
    private String path;
    private String parentId;
    private List<String> childrenId;
    private String type;
    private boolean show;
    private boolean expand;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String creator;
    private String modifier;
}
