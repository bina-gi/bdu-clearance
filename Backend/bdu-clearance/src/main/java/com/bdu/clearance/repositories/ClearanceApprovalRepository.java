package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.ClearanceApproval;

@Repository
public interface ClearanceApprovalRepository extends JpaRepository<ClearanceApproval, Long> {
    java.util.List<ClearanceApproval> findByClearanceId(Long clearanceId);
}
