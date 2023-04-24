package com.hariesbackend.contents.controller;

import com.hariesbackend.contents.dto.DocumentsInfo;
import com.hariesbackend.contents.dto.PaginationDTO;
import com.hariesbackend.contents.service.DocumentsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("documents")
@Slf4j
public class DocumentsController {
    @Autowired
    private DocumentsService documentsService;

    // 글 쓰기
    @PostMapping("/create")
    public void createDocuments(
            @RequestBody DocumentsInfo.DocumentDTO data
    ) {
        try {
            documentsService.createDocuments(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 모든 글데이터 가져오기
    @GetMapping("/get-all")
    public DocumentsInfo getAllDocuments(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        try {
            return documentsService.getAllDocuments(new PaginationDTO(page, size));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 특정 글 가져오기
    @GetMapping("/get")
    public DocumentsInfo.DocumentDTO getDocument(@RequestParam("id") String id) {
        try {
            return documentsService.getDocument(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/delete")
    public void deleteDocument(@RequestParam("id") String id) {
        try {
            documentsService.deleteDocument(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/save")
    public void saveDocument(
            @RequestParam("id") String id,
            @RequestBody DocumentsInfo.DocumentDTO data
    ) {
        try {
            documentsService.saveDocument(id, data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
