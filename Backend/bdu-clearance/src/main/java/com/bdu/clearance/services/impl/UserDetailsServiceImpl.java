package com.bdu.clearance.services.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // repository for Users entity

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getRoleName().name()))
                .accountLocked(!user.getIsActive())
                .build();
    }
}

