package com.bdu.clearance.controllers.user;

import com.bdu.clearance.jwt.JwtUtils;
import com.bdu.clearance.jwt.LoginRequest;
import com.bdu.clearance.jwt.LoginResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(
                            "Bad credentials",
                            HttpStatus.UNAUTHORIZED.value(),
                            System.currentTimeMillis()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication);

        long expiresAt = System.currentTimeMillis() + jwtUtils.getExpirationMillis();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(
                userDetails.getUsername(),
                roles,
                jwt,
                expiresAt);

        return ResponseEntity.ok(response);
    }

    public record ErrorResponse(String message, int status, long timestamp) {
    }
}
