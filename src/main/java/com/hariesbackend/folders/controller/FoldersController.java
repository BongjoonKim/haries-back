package com.hariesbackend.folders.controller;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.service.FoldersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("folders")
@Slf4j
public class FoldersController {
    @Autowired
    private FoldersService foldersService;

    @PostMapping("/create")
    public void createFolder(@RequestBody FoldersDTO foldersDTO) {
        try {
            foldersService.createFolders(foldersDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Root 폴더 조회
    @GetMapping("/get/root")
    public FoldersEntity getRootFolder() {
        try {
            return foldersService.getRootFolder();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 특정 폴더 조회
    @GetMapping("/get")
    public FoldersEntity getFolder(@RequestParam("id") String id) {
        try {
            return foldersService.getFolder(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 해당 폴더 하위 폴더 조회
    @GetMapping("/get/children")
    public List<FoldersEntity> getFolderList(
            @RequestParam("parentId") String parentId,
            @RequestParam("depth") int depth
    ) {
        try {
            return foldersService.getExpandedFolders(parentId, depth);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
