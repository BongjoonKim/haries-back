package com.hariesbackend.login.service;

import com.hariesbackend.login.model.NaverUser;
import com.hariesbackend.login.model.ProviderUser;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AbstractOAuth2UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        Users users = usersRepository.findByUserName(providerUser.getUsername());
        if (users == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {

        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();
        if(registrationId.equals("naver")) {
            return new NaverUser(oAuth2User, clientRegistration);
        }
        return null;
    }


}
