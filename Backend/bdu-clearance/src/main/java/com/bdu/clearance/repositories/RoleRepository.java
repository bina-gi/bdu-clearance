package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdu.clearance.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
