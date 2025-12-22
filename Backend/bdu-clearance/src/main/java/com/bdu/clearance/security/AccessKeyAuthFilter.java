package com.bdu.clearance.security;

import com.bdu.clearance.models.AccessKey;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.services.AccessKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class AccessKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-Key";

    private final AccessKeyService accessKeyService;

    public AccessKeyAuthFilter(@Lazy AccessKeyService accessKeyService) {
        this.accessKeyService = accessKeyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Skip if already authenticated (e.g., by JWT filter)
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey != null && !apiKey.isBlank()) {
            try {
                Optional<AccessKey> keyOpt = accessKeyService.validateKey(apiKey);

                if (keyOpt.isPresent()) {
                    AccessKey accessKey = keyOpt.get();
                    Users user = accessKey.getUser();

                    if (user != null && user.getIsActive() != null && user.getIsActive()) {
                        // Get role from user
                        String roleName = "ROLE_" + user.getUserRole().getRoleName().name();

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user.getUserId(),
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(roleName)));

                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Update last used timestamp async
                        accessKeyService.updateLastUsed(accessKey);

                        log.debug("API Key authentication successful for user: {}", user.getUserId());
                    }
                } else {
                    log.warn("Invalid API key attempt from IP: {}", request.getRemoteAddr());
                }
            } catch (Exception e) {
                log.error("Error during API key authentication: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
