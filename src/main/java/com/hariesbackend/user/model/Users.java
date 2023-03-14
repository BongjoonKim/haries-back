package com.hariesbackend.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    private String id;
    private String address;
    private String email;
    private String modified;
    private String userId;
    private String userPassword;
    private String address2;
    private String email2;
    private String phoneNumber;
    private String address4;
    private String address5;
    private String authGroup;
    private String authority;
    private String created;

}
