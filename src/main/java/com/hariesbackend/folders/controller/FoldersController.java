package com.hariesbackend.folders.controller;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.service.FoldersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/folders")
@Slf4j
public class FoldersController {
    @Autowired
    private FoldersService foldersService;

    @PostMapping("")
    public ResponseEntity<?> createFolder(@RequestBody FoldersDTO foldersDTO) {
        try {
            foldersService.createFolders(foldersDTO);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Root 폴더 조회
    @GetMapping("/ps/root")
    public FoldersDTO getRootFolder() {
        try {
            return foldersService.getRootFolder();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 특정 폴더 조회
    @GetMapping("/ps/folder-id")
    public FoldersDTO getFolder(@RequestParam("id") String id) {
        try {
            return foldersService.getFolder(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/get/unique-key")
    public FoldersEntity getFolderByUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        try {
            return foldersService.getFolderByUniqueKey(uniqueKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 해당 폴더 하위 폴더 조회
    @GetMapping("/ps/children")
    public List<FoldersDTO> getChildFolders(
            @RequestParam("parentId") String parentId) {
        try {
            return foldersService.getChildFolders(parentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("")
    public FoldersDTO editFolders(
            @RequestBody FoldersDTO foldersDTO
    ) {
        try {
            return foldersService.modifyFolders(foldersDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("")
    public void deleteFolder(
            @RequestParam("id") String id
    ) {
        try {
            foldersService.deleteFolder(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
