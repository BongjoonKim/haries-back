package com.hariesbackend.common.repository;

import com.hariesbackend.common.model.Menus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MenuRepository extends MongoRepository<Menus, String> {

}
