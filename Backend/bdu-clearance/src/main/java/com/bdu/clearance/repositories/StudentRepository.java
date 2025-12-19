package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.StudentStatus;
import com.bdu.clearance.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserUserId(String studentId);

    Optional<Student> findByUserId(Long userId);

    List<Student> findByAdvisorId(Long advisorId);

    List<Student> findByYearOfStudy(int year);

    List<Student> findByStudentStatus(StudentStatus status);
}
