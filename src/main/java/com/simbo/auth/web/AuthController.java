package com.simbo.auth.web;

import com.simbo.auth.document.User;
import com.simbo.auth.dto.LoginDTO;
import com.simbo.auth.dto.SignupDTO;
import com.simbo.auth.dto.TokenDTO;
import com.simbo.auth.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SignupDTO singupDTO) {
        User user = new User(singupDTO.getEmail(), singupDTO.getPassword());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated(user, singupDTO.getPassword(), Collections.emptyList());
        return  ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.
                authenticate(
                        UsernamePasswordAuthenticationToken.
                                unauthenticated(
                                        loginDTO.getEmail(),
                                        loginDTO.getPassword())
                );
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDTO tokenDto) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDto.getRefreshToken()));

        Jwt jwt = (Jwt) authentication.getCredentials();
        //check if in db

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }
}
