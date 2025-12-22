package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.ApprovalStatus;
import com.bdu.clearance.models.ClearanceApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearanceApprovalRepository extends JpaRepository<ClearanceApproval, Long> {

    List<ClearanceApproval> findByClearanceId(Long clearanceId);

    /**
     * Find pending approvals for a specific organizational unit.
     * Used for staff dashboard to see their pending work.
     */
    @Query("SELECT ca FROM ClearanceApproval ca " +
            "WHERE ca.organizationalUnit.id = :orgId " +
            "AND ca.status = 'PENDING'")
    List<ClearanceApproval> findPendingByOrganizationalUnit(@Param("orgId") Long orgId);

    /**
     * Find pending approvals for multiple organizational units.
     * Useful when staff may approve for multiple units.
     */
    @Query("SELECT ca FROM ClearanceApproval ca " +
            "WHERE ca.organizationalUnit.id IN :orgIds " +
            "AND ca.status = 'PENDING'")
    List<ClearanceApproval> findPendingByOrganizationalUnits(@Param("orgIds") List<Long> orgIds);

    /**
     * Find approvals by status.
     */
    List<ClearanceApproval> findByStatus(ApprovalStatus status);

    /**
     * Find approvals processed by a specific user.
     */
    List<ClearanceApproval> findByApprovedById(Long userId);
}
