package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.OrganizationalUnitType;

@Repository
public interface OrganizationalUnitTypeRepository extends JpaRepository<OrganizationalUnitType, Long> {
}
