package com.hariesbackend.contents.controller;

import com.hariesbackend.contents.dto.DocumentsDTO;
import com.hariesbackend.contents.service.DocumentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/documents")
@Slf4j
public class DocumentsController {
    @Autowired
    private DocumentsService documentsService;

    // 글 쓰기
    @PostMapping("/create")
    public void createDocuments(
            @RequestBody DocumentsDTO data) {
        try {
            documentsService.createDocuments(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 글 데이터 가져오기
}
