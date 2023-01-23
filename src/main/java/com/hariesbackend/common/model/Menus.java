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
    private ObjectId _id;
    private String menuName;
    private String menuHref;
    private String menuLabel;

//    public Menus(ObjectId _id, String menuName, String menuHref, String menuLabel) {
//        this._id = _id;
//        this.menuHref = menuHref;
//        this.menuLabel = menuLabel;
//        this.menuName = menuName;
//    }
}
