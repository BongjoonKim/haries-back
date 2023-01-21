package com.example.hariesbackend.common.service;

import com.example.hariesbackend.common.model.Menu;
import com.example.hariesbackend.common.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public Menu getAllMenuList() {
        Menu result = menuRepository.findByMenuHref("frontEnd");
        return result;
    }
}
