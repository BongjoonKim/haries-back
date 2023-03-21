package com.hariesbackend.contents.controller;

import com.hariesbackend.common.service.MenuService;
import com.hariesbackend.contents.model.Documents;
import com.hariesbackend.contents.service.DocumentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
@Slf4j
public class DocumentsController {
    @Autowired
    private DocumentsService documentsService;

    @PostMapping("/create")
    public void createDocuments(Documents data) {
        try {
            documentsService.createDocuments(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
