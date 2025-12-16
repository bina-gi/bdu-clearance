package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.OrganizationalUnit;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long>   {
}
