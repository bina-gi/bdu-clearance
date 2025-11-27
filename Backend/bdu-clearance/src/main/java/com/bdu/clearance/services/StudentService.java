package com.bdu.clearance.services;

import com.bdu.clearance.models.Student;

import java.util.List;


public interface StudentService {
    List<Student> getAllStudent();
    void createStudent(Student student);
    String deleteStudent(String studentId);
    Student updateStudent(Student student,Long id);

    Student getStudent(String studentId);
    List<Student> getStudentByFaculty(String faculty);
    List<Student> getStudentByDepartment(String department);
    List<Student> getStudentByAdvisor(String advisor);










}
