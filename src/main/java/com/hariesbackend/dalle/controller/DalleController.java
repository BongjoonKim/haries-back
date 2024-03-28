package com.hariesbackend.dalle.controller;

import com.hariesbackend.dalle.dto.DalleReqDTO;
import com.hariesbackend.dalle.dto.DalleResDTO;
import com.hariesbackend.dalle.service.DalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dalle")
@Slf4j
public class DalleController {
    @Autowired
    private DalleService dalleService;

    @PostMapping("/image")
    public DalleResDTO createImage(@RequestBody String question) {
        return dalleService.DalleAnswer(question);
    }

    @GetMapping("/images")
    public List<DalleResDTO> getImages() {
        return dalleService.getDalleImages();
    }
}
