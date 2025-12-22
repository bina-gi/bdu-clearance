package com.bdu.clearance.services.impl;

import com.bdu.clearance.models.AccessKey;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.AccessKeyRepository;
import com.bdu.clearance.services.AccessKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessKeyServiceImpl implements AccessKeyService {

    private final AccessKeyRepository accessKeyRepository;
    private final PasswordEncoder passwordEncoder;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int KEY_LENGTH = 32;
    private static final int PREFIX_LENGTH = 8;
    private static final int DEFAULT_EXPIRATION_DAYS = 90;

    @Override
    @Transactional
    public AccessKey generateKey(Users user, String description, Integer expirationDays) {
        String rawKey = generateSecureKey();
        String prefix = rawKey.substring(0, PREFIX_LENGTH);
        String keyHash = passwordEncoder.encode(rawKey);

        AccessKey accessKey = new AccessKey();
        accessKey.setUser(user);
        accessKey.setKeyPrefix(prefix);
        accessKey.setKeyHash(keyHash);
        accessKey.setDescription(description);
        accessKey.setIsActive(true);
        accessKey.setIsRevoked(false);

        int expDays = expirationDays != null ? expirationDays : DEFAULT_EXPIRATION_DAYS;
        if (expDays > 0) {
            accessKey.setExpiresAt(LocalDateTime.now().plusDays(expDays));
        }

        AccessKey saved = accessKeyRepository.save(accessKey);

        log.info("Generated access key {} for user {}", prefix + "...", user.getUserId());

        // Return the raw key temporarily attached for the caller
        saved.setKeyHash(rawKey); // Temporarily set raw key for response
        return saved;
    }

    @Override
    public Optional<AccessKey> validateKey(String rawKey) {
        if (rawKey == null || rawKey.length() < PREFIX_LENGTH) {
            return Optional.empty();
        }

        String prefix = rawKey.substring(0, PREFIX_LENGTH);
        List<AccessKey> candidates = accessKeyRepository.findActiveByKeyPrefix(prefix);

        for (AccessKey candidate : candidates) {
            // Check expiration
            if (candidate.getExpiresAt() != null &&
                    candidate.getExpiresAt().isBefore(LocalDateTime.now())) {
                continue;
            }

            // Verify hash
            if (passwordEncoder.matches(rawKey, candidate.getKeyHash())) {
                return Optional.of(candidate);
            }
        }

        log.warn("Invalid access key attempt with prefix: {}", prefix);
        return Optional.empty();
    }

    @Override
    @Transactional
    public void revokeKey(Long keyId, Long userId) {
        AccessKey key = accessKeyRepository.findByIdAndUserId(keyId, userId)
                .orElseThrow(() -> new RuntimeException("Access key not found"));
        key.setIsRevoked(true);
        key.setIsActive(false);
        accessKeyRepository.save(key);
        log.info("Revoked access key {} for user {}", key.getKeyPrefix(), userId);
    }

    @Override
    @Transactional
    public void revokeAllKeysForUser(Long userId) {
        List<AccessKey> keys = accessKeyRepository.findByUserIdAndIsActiveTrue(userId);
        for (AccessKey key : keys) {
            key.setIsRevoked(true);
            key.setIsActive(false);
        }
        accessKeyRepository.saveAll(keys);
        log.info("Revoked {} access keys for user {}", keys.size(), userId);
    }

    @Override
    public List<AccessKey> getKeysForUser(Long userId) {
        return accessKeyRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void updateLastUsed(AccessKey key) {
        key.setLastUsedAt(LocalDateTime.now());
        accessKeyRepository.save(key);
    }

    private String generateSecureKey() {
        byte[] bytes = new byte[KEY_LENGTH];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
