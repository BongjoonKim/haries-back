package com.hariesbackend.login.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}
