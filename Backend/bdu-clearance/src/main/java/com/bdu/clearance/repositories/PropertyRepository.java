package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.Property;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByStudentId(Long studentId);

    /**
     * Find properties by borrow status for a specific organizational unit.
     * Used for staff to view outstanding borrowed items in their department.
     */
    List<Property> findByBorrowStatusAndOrganizationalUnitId(BorrowStatus status, Long orgId);

    /**
     * Find properties by student and borrow status.
     * Used for student dashboard to see their borrowed/outstanding items.
     */
    List<Property> findByStudentIdAndBorrowStatus(Long studentId, BorrowStatus status);

    /**
     * Find all outstanding (borrowed) items across the system.
     */
    List<Property> findByBorrowStatus(BorrowStatus status);
}
