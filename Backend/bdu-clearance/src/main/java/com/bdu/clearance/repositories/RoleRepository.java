package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.UserRole;
import com.bdu.clearance.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole roleName);

    boolean existsByRoleName(UserRole roleName);
}
