package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdu.clearance.models.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
