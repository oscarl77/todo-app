package com.oscarl.todobackend.authentication;

import com.oscarl.todobackend.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (authService.isLoginValid(email, password)) {
            // Generate JWT token and store as HTTP-only cookie
            String token = authService.generateToken(email);
            ResponseCookie jwtCookie = createJwtCookie(token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    /**
     * Creates an HTTP-only cookie to store JWT token.
     * @param token the JWT token.
     * @return the HTTP-only cookie.
     */
    private ResponseCookie createJwtCookie(String token) {
        int SECONDS = 60;
        int MINUTES = 60;
        return ResponseCookie.from("jwt", token)
                .httpOnly(true) // Make cookie inaccessible via JavaScript
                .secure(true)
                .path("/") // Cookie is valid for all endpoints
                .maxAge(SECONDS * MINUTES)
                .sameSite("Strict") // CSRF protection
                .build();
    }


}
