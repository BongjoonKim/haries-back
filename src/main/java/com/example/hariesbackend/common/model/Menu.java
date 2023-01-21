package com.example.hariesbackend.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="Menu")
public class Menu {
    @Id
    private String id;
    private String menuName;
    private String menuHref;
    private String menuLabel;
}
