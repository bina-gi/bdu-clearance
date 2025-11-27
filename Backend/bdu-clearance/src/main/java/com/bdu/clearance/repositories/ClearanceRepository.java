package com.bdu.clearance.repositories;

import com.bdu.clearance.models.Clearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClearanceRepository extends JpaRepository<Clearance, Long> {


    Clearance findByStudentId(String studentId);
}