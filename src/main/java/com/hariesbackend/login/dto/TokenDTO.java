package com.hariesbackend.login.dto;

import com.hariesbackend.login.model.Tokens;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDTO  {
    private String refreshToken;
    private String accessToken;
    private Collection<? extends GrantedAuthority> grantType;
}

