package com.bdu.clearance.repositories;

import com.bdu.clearance.models.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {

    @Query("SELECT ak FROM AccessKey ak WHERE ak.keyPrefix = :prefix AND ak.isActive = true AND ak.isRevoked = false")
    List<AccessKey> findActiveByKeyPrefix(@Param("prefix") String prefix);

    List<AccessKey> findByUserId(Long userId);

    List<AccessKey> findByUserIdAndIsActiveTrue(Long userId);

    Optional<AccessKey> findByIdAndUserId(Long id, Long userId);
}
