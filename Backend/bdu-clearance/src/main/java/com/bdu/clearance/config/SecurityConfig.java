package com.bdu.clearance.config;

import com.bdu.clearance.jwt.AuthEntryPointJwt;
import com.bdu.clearance.jwt.AuthTokenFilter;
import com.bdu.clearance.services.impl.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
            AuthEntryPointJwt unauthorizedHandler,
            AuthTokenFilter authTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authTokenFilter = authTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http
    // .csrf(csrf -> csrf.disable())
    // .exceptionHandling(exception ->
    // exception.authenticationEntryPoint(unauthorizedHandler))
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/api/auth/**").permitAll()
    // .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
    // .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
    // .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
    // .anyRequest().authenticated())
    // .formLogin(form -> form.disable())
    // .httpBasic(basic -> basic.disable());

    // http.addFilterBefore(authTokenFilter,
    // UsernamePasswordAuthenticationFilter.class);

    // return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

}
