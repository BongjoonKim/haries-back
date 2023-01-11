package com.example.hariesbackend.common.repository;

import com.example.hariesbackend.common.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<Menu, String> {
    Menu findAllby();
}
