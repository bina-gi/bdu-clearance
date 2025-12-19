package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    java.util.List<Property> findByStudentId(Long studentId);
}
