package com.bdu.clearance.services;

import com.bdu.clearance.models.AccessKey;
import com.bdu.clearance.models.Users;

import java.util.List;
import java.util.Optional;

public interface AccessKeyService {

    AccessKey generateKey(Users user, String description, Integer expirationDays);

    Optional<AccessKey> validateKey(String rawKey);

    void revokeKey(Long keyId, Long userId);

    void revokeAllKeysForUser(Long userId);

    List<AccessKey> getKeysForUser(Long userId);

    void updateLastUsed(AccessKey key);
}
