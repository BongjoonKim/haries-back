package com.hariesbackend.folders.service;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;

import java.util.List;

public interface FoldersService {
    List<FoldersDTO> getChildFolders (String parentId);

    FoldersDTO getRootFolder ();

    void createFolders(FoldersDTO foldersDTO);

    FoldersDTO getFolder(String id);

    FoldersEntity getFolderByUniqueKey(String uniqueKey);

    FoldersDTO modifyFolders(FoldersDTO foldersDTO);

    void deleteFolder(String id);
}
