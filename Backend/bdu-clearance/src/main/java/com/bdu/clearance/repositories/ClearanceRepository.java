package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.ClearanceStatus;
import com.bdu.clearance.enums.Semester;
import com.bdu.clearance.models.Clearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearanceRepository extends JpaRepository<Clearance, Long> {

    List<Clearance> findByStudentUserUserId(String studentId);

    List<Clearance> findByAcademicYear(int academicYear);

    List<Clearance> findByYearOfStudy(int yearOfStudy);

    List<Clearance> findBySemester(Semester semester);

    List<Clearance> findByReason(String reason);

    List<Clearance> findByStatus(ClearanceStatus status);

    List<Clearance> findByStudentId(Long studentId);
}
