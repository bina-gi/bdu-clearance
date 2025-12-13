package com.bdu.clearance.repositories;

import com.bdu.clearance.models.Users;
import com.bdu.clearance.enums.UserRole;
import com.bdu.clearance.enums.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(String userId);

    List<Users> findByRole(UserRole role);

    List<Users> findByCampus(Campus campus);

    List<Users> findByIsActive(Boolean isActive);
}