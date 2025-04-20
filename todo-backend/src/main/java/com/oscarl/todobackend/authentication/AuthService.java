package com.oscarl.todobackend.authentication;


import com.oscarl.todobackend.service.UserService;
import com.oscarl.todobackend.model.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service class that handles authentication logic such as password encoding
 * and JWT cookie creation.
 */
@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final Key key;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        // Retrieve JWT secret key from env variables
        Dotenv dotenv = Dotenv.load();
        String SECRET_KEY = dotenv.get("jwtkey");
        if (SECRET_KEY == null) {
            throw new IllegalArgumentException("JWT key not set");
        }
        // Generate HMAC SHA Key
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Check whether provided user credentials are valid.
     * @param email the inputted email address.
     * @param password the inputted password.
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean isLoginValid(String email, String password) {
        String passwordHash;
        try {
            User user = userService.getUserByEmail(email);
            passwordHash = userService.getPasswordByEmail(email);
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return passwordEncoder.matches(password, passwordHash);
    }

    /**
     * Generate a JSON Web Token.
     * @param email the user's email address.
     * @return a JSON Web Token.
     */
    private String generateToken(String email) {
        User user = userService.getUserByEmail(email);
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key)
                .compact(); // Convert token builder into usable string
    }

    /**
     * Creates an HTTP-only cookie to store JWT token.
     * @param email the user's email address.
     * @return the HTTP-only cookie.
     */
    public ResponseCookie createJwtCookie(String email) {
        int SECONDS_PER_MIN = 60;
        int MINUTES = 60;
        String token = generateToken(email);
        return ResponseCookie.from("jwt", token)
                .httpOnly(true) // Make cookie inaccessible via JavaScript
                .secure(true) // Ensure cookie is only sent via HTTPS
                .path("/") // Cookie is valid for all endpoints
                .maxAge(SECONDS_PER_MIN * MINUTES)
                .sameSite("Strict") // CSRF protection
                .build();
    }
}
