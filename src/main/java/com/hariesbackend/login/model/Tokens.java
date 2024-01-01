package com.hariesbackend.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tokens {
    @Id
    private String id;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String email;
}
