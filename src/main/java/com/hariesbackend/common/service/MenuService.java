package com.hariesbackend.common.service;

import com.hariesbackend.common.model.Menus;
import com.hariesbackend.common.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public Menus getAllMenuList() {

        List<Menus> resultList = menuRepository.findAll();
        if (resultList.isEmpty()) {
            Menus menus = new Menus();
            menus.setMenuName("111");
            resultList.add(menus);
            return resultList.get(0);
        } else {
            return resultList.get(0);
        }
    }
}
