package com.hariesbackend.login.service;

import com.hariesbackend.login.model.ProviderUser;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    public void register(String registrationId, ProviderUser providerUser) {
            LocalDateTime now = LocalDateTime.now();
            Users users = Users.builder()
                    .registrationId(registrationId)
                    .id(providerUser.getId())
                    .userName(providerUser.getUsername())
                    .userPassword(providerUser.getPassword())
                    .provider(providerUser.getProvider())
                    .email(providerUser.getEmail())
//                    .authorities(providerUser.getAuthorities())
                    .active(true)
//                    .created(now)
//                    .modified(now)
                    .build();

            usersRepository.save(users);


    }
}
