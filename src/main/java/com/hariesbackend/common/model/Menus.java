package com.hariesbackend.common.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Getter
//@Setter
//@Document(collection="menus")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menus {
    @Id
    private String id;
    private String menuName;
    private String menuHref;
    private String menuLabel;
}
