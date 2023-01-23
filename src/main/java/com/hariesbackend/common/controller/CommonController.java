package com.hariesbackend.common.controller;

import com.hariesbackend.common.model.Menus;
import com.hariesbackend.common.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/test")
    public Menus getMenu() {
        try {
            Menus menuList = menuService.getAllMenuList();
            return menuList;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}