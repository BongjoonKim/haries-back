package com.hariesbackend.folders.service;

import com.hariesbackend.folders.dto.FoldersDTO;

import java.util.List;

public interface FoldersService {
    List<FoldersDTO> getAllExpandedFolders ();

    void createFolders(FoldersDTO foldersDTO);

    void modifyFolders(String id, FoldersDTO foldersDTO);
}
