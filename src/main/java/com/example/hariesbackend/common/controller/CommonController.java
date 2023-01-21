package com.example.hariesbackend.common.controller;

import com.example.hariesbackend.common.model.Menu;
import com.example.hariesbackend.common.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    MenuService menuService;

    @GetMapping("/test")
    public Menu getMenu() {
        try {
            Menu menuList = menuService.getAllMenuList();
            return menuList;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}