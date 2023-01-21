package com.example.hariesbackend.common.repository;

import com.example.hariesbackend.common.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {

    Menu findByMenuHref(String href);
}
