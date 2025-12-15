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
    List<StudentResponseDto> getStudentByFaculty(Faculty faculty);
    List<StudentResponseDto> getStudentByDepartment(Department department);
    List<StudentResponseDto> getStudentByAdvisor(String advisorId);
    List<StudentResponseDto> getStudentByStatus(StudentStatus status);
    List<StudentResponseDto> getStudentByCampus(Campus campus);
    List<StudentResponseDto> getStudentByYear(int year);
}
    //Update sectors
//    Student updateLibraryStatus(Student student, Long studentId);
//    Student updateCafeteriaStatus(Student student,Long studentId);
//    Student updateDormitoryStatus(Student student,Long studentId);
//    Student updateFacultyStoreStatus(Student student,Long studentId);
//    Student updateAdvisorStatus(Student student,Long studentId);
//    Student updateFinanceStatus(Student student,Long studentId);
//    Student updateFacultyStatus(Student student,Long studentId);
//    Student updateRegistrarStatus(Student student,Long studentId);










