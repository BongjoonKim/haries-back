package com.hariesbackend.folders.service;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;

import java.util.List;

public interface FoldersService {
    List<FoldersEntity> getExpandedFolders (String parentId, int depth);

    FoldersEntity getRootFolder ();

    void createFolders(FoldersDTO foldersDTO);

    FoldersEntity getFolder(String id);

    void modifyFolders(String id, FoldersDTO foldersDTO);
}
