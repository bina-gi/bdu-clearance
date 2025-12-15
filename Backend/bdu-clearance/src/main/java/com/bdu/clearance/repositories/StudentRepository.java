package com.bdu.clearance.repositories;

import com.bdu.clearance.enums.StudentStatus;
import com.bdu.clearance.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUserId(String studentId);
    List<Student> findByFaculty(Faculty faculty);
    List<Student> findByDepartment(Department department);
    List<Student> findByAdvisor(String advisorId);
    List<Student> findByYear(int year);
    List<Student> findByStatus(StudentStatus status);
    List<Student> findByCampus(Campus campus);
}
