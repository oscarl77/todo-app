package com.oscarl.todobackend.authentication;


import com.oscarl.todobackend.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final Key key;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;


        Dotenv dotenv = Dotenv.load();
        String SECRET_KEY = dotenv.get("jwtkey");
        if (SECRET_KEY == null) {
            throw new IllegalArgumentException("JWT key not set");
        }
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isLoginValid(String email, String password) {
        String passwordHash;
        try {
            passwordHash = userService.getPasswordByEmail(email);
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return passwordEncoder.matches(password, passwordHash);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key)
                .compact();
    }
}
