package com.hariesbackend.chatting.model;

import com.hariesbackend.login.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Channels {

    @Id
    private String id;
    private String name;
    private String detail;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<Authority> authorities;


}
