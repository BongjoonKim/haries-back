package com.hariesbackend.login.service.serviceImpl;

import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    UsersRepository usersRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            Users users = usersRepository.findByUserName(userName);
            List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("Admin"));
            User user = new User(users.getUserId(), users.getUserPassword(), grantedAuthorities);
            return user;
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }
}
