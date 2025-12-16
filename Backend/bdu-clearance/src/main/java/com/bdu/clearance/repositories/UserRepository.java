package com.bdu.clearance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(String userId);

//    List<Users> findByRole(UserRole role);

//    List<Users> findByCampus(Campus campus);

    List<Users> findByIsActive(Boolean isActive);
}