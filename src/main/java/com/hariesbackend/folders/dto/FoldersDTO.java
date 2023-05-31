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
public class FoldersDTO extends FoldersEntity {
    private List<String> children;

}
