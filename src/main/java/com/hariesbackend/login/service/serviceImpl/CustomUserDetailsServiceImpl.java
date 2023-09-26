package com.hariesbackend.login.service.serviceImpl;

import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import com.hariesbackend.login.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            Users user = usersRepository.findByUserId(userId);
            UserDetails userDetails = (UserDetails) user;
            return userDetails;
        } catch (UsernameNotFoundException e) {
            throw e;
        }

    }
}
