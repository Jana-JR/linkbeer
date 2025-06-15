package org.jana.urlshortnersb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jana.urlshortnersb.dtos.LoginRequest;
import org.jana.urlshortnersb.dtos.RegisterRequest;
import org.jana.urlshortnersb.models.User;
import org.jana.urlshortnersb.security.jwt.JwtAuthenticationResponse;
import org.jana.urlshortnersb.security.jwt.JwtUtils;
import org.jana.urlshortnersb.service.UserDetailsImpl;
import org.jana.urlshortnersb.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;

    @PostMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        JwtAuthenticationResponse tokens = userService.authenticateUser(loginRequest);
        
        // Set refresh token as HttpOnly cookie
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
            .httpOnly(true)
            .secure(true)  // Enable in production
            .path("/")
            .maxAge(Duration.ofDays(7).toSeconds())
            .sameSite("Strict")
            .build();
        
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        
        // Return only access token in response body
        return ResponseEntity.ok(Collections.singletonMap("accessToken", tokens.getAccessToken()));
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole("ROLE_USER");
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }
        
        try {
            if (!jwtUtils.validateToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }
            
            String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtils.generateAccessToken(userDetails);
            
            // Return new access token in response body
            return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refreshing token: " + e.getMessage());
        }
    }
}
