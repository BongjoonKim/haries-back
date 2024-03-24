package com.hariesbackend.dalle.controller;

import com.hariesbackend.dalle.service.DalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/dalle")
@Slf4j
public class DalleController {
    @Autowired
    private DalleService dalleService;

    @PostMapping("/image")
    public String getImage(@RequestBody HashMap<String, Object> request) {

    }
}
