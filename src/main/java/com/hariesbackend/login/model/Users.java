package com.hariesbackend.login.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends UserCommon {
    @Id
    private String id;

    private String registrationId;
    private boolean active;
    private String email;
    private String userId;
    private String userPassword;
    private String password;
    private String src;
    private String userName;
    private String provider;
    private String nickname;
    private int age;
    private String ageRange;
    private String gender;
    private List<String> roles = new ArrayList<>();
    private String address;
    private String emailSecond;
    private String phoneNumber;
    private String phoneNumberE164;
    private String profileImg;
    private Date birthday;
    private boolean isBot;



    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

//    @Override
//    public String getUsername() {
//        return this.userId;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.userPassword;
//    }
//
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
