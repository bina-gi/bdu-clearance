package com.bdu.clearance.services;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.enums.StudentStatus;

import java.util.List;

public interface StudentService {

    List<StudentResponseDto> getAllStudents();

    void createStudent(StudentRequestDto studentDto);

    void deleteStudent(String studentId);

    void updateStudent(StudentRequestDto studentDto, Long id);

    StudentResponseDto getStudent(String studentId);

    List<StudentResponseDto> getStudentByAdvisor(Long advisorId);

    List<StudentResponseDto> getStudentByStatus(StudentStatus status);

    List<StudentResponseDto> getStudentByYearOfStudy(int year);
}
