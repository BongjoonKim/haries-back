package com.hariesbackend.contents.controller;

import com.hariesbackend.contents.dto.DocumentInfo;
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
            @RequestBody DocumentInfo.DocumentsDTO data
    ) {
        try {
            documentsService.createDocuments(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 글 데이터 가져오기
    @GetMapping("/get-all")
    public DocumentInfo getAllDocuments(
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
}
