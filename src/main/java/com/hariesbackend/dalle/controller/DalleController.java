package com.hariesbackend.dalle.controller;

import com.hariesbackend.dalle.dto.DalleDTO;
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
    public void createImage(@RequestBody HashMap<String, Object> req) {
        System.out.println("req = " + req);
        try {
            dalleService.DalleAnswer((String) req.get("question"));
        } catch (Exception e) {
            System.out.println("DalleController.createImage error" + e);
        }
    }

    @GetMapping("/images")
    public List<DalleDTO> getImages() {
        return dalleService.getDalleImages();
    }

    @GetMapping("/image")
    public DalleDTO getImage(@RequestParam("id") String id) {
        return dalleService.getDalleIamge(id);
    }

    @DeleteMapping("/image")
    public void deleteImage(@RequestParam("id") String id) {
        try {
            dalleService.deleteDalleImage(id);
        } catch (Exception e) {
            System.out.println("DalleController.deleteImage error" + e);
        }

    }
}
