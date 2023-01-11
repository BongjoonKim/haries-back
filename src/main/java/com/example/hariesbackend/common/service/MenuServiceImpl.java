package com.example.hariesbackend.common.service;

import com.example.hariesbackend.common.model.Menu;
import com.example.hariesbackend.common.repository.MenuRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<Menu> getAllMenuList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (menuRepository.findAll() != null) {
            log.info("여기야여기");
        }
        return null;
    }

}
