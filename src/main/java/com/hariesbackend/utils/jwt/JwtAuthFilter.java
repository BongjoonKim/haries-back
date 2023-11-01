//package com.hariesbackend.utils.jwt;
//
//import com.hariesbackend.login.model.Users;
//import com.hariesbackend.login.repository.UsersRepository;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Slf4j
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UsersRepository usersRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (!jwtTokenProvider.verifyToken(token)) {
//            throw new JwtException("Access 토큰 만료");
//        }
//
//        if (jwtTokenProvider.verifyToken(token)) {
//            Users user = usersRepository.findByEmail(jwtTokenProvider.extractSubject(token));
//
//            Authentication auth = getAuthentication(user);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    public Authentication getAuthentication(Users user) {
//        return new UsernamePasswordAuthenticationToken(user, "",
//            List.of(new SimpleGrantedAuthority("admin"))
//        );
//    }
//}
