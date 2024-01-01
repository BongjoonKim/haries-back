package com.hariesbackend.common.controller;

import com.hariesbackend.common.model.Menus;
import com.hariesbackend.common.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public List<Menus> retrieveMenus() {
        try {
            return menuService.getAllMenuList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}