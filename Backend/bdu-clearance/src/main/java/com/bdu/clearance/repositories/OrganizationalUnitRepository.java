package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.OrganizationalUnit;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

    Optional<OrganizationalUnit> findByOrganizationId(String organizationId);

    List<OrganizationalUnit> findByParentOrganizationId(String parentOrganizationId);

    /**
     * Find organizational units by parent ID and organization type.
     * Used for routing logic to find specific approvers (e.g., stores, library)
     * under a parent org.
     */
    @Query("SELECT ou FROM OrganizationalUnit ou " +
            "WHERE ou.parent.id = :parentId " +
            "AND ou.organizationalUnitType.organizationType = :type")
    List<OrganizationalUnit> findByParentIdAndType(@Param("parentId") Long parentId,
            @Param("type") String type);

    /**
     * Find all child organizational units under a parent.
     */
    List<OrganizationalUnit> findByParentId(Long parentId);
}
