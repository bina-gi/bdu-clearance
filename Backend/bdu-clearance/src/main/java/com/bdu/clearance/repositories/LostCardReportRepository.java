package com.bdu.clearance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdu.clearance.models.LostCardReport;

public interface LostCardReportRepository extends JpaRepository<LostCardReport, Long> {
}
