package com.bdu.clearance.repositories;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.models.Clearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearanceRepository extends JpaRepository<Clearance, Long> {

    List<ClearanceResponseDto> findByStudentId(String studentId);
    List<ClearanceResponseDto> findByAcademicYear(int academicYear);
    List<ClearanceResponseDto> findByYearOfStudy(int yearOfStudy);
    List<ClearanceResponseDto> findBySemester(int semester);
    List<ClearanceResponseDto> findByReason(String reason);
    List<ClearanceResponseDto> findByStatus(Boolean isCleared);

}