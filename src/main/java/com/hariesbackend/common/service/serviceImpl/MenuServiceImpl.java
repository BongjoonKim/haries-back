package com.hariesbackend.common.service.serviceImpl;

import com.hariesbackend.common.model.Menus;
import com.hariesbackend.common.repository.MenuRepository;
import com.hariesbackend.common.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Menus> getAllMenuList() {
        return menuRepository.findAll();
    }



}
