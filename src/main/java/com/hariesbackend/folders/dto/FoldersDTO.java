package com.hariesbackend.folders.dto;

import com.hariesbackend.folders.model.FoldersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoldersDTO {
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
    private List<FoldersDTO> children;

}
