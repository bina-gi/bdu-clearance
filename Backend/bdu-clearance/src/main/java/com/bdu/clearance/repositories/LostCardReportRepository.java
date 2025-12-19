package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdu.clearance.models.LostCardReport;

@Repository
public interface LostCardReportRepository extends JpaRepository<LostCardReport, Long> {
    java.util.List<LostCardReport> findByStudentId(Long studentId);
}
