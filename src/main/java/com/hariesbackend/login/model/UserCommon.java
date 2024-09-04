package com.hariesbackend.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCommon {
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean isNonExpired;
    private boolean isNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
