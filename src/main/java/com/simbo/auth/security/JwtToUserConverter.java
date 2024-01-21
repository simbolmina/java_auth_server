package com.simbo.auth.security;

import com.simbo.auth.document.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private static final Logger logger = LoggerFactory.getLogger(JwtToUserConverter.class);


    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        System.out.println("JWT: " + jwt);
        System.out.println("JWT: " + jwt.getClaims());
        System.out.println("JWT: " + jwt.getSubject());
        User user = new User();
        user.setId(jwt.getSubject());
//        String role = jwt.getClaimAsString("role");
        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList());
    }

//    @Override
//    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
//        String role = jwt.getClaimAsString("role"); // Adjust the claim name as needed
//        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
//
//        String principal = jwt.getSubject(); // or a custom UserDetails object
//
//        User user = new User();
//        user.setId(jwt.getSubject());
//        return new UsernamePasswordAuthenticationToken(principal, jwt.getTokenValue(), authorities);
//    }

//    @Override
//    public UsernamePasswordAuthenticationToken convert(@NonNull Jwt jwt) {
//        // Log the entire JWT object
//        logger.info("JWT: {}", jwt);
//        logger.info("JWT Subject: {}", jwt.getSubject());
//        logger.info("JWT Claims: {}", jwt.getClaims());
//        logger.info("JWT Role: {}", jwt.getClaimAsString("role"));
//
//        User user = new User();
//        user.setId(jwt.getSubject());
//        String role = jwt.getClaimAsString("role");
//        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
//        return new UsernamePasswordAuthenticationToken(user, null, authorities);
//    }



}
