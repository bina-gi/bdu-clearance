package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.enums.StudentStatus;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.StudentMapper;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.services.StudentService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;



    @Override
    public List<StudentResponseDto> getAllStudents() {
        List<StudentResponseDto> response = studentMapper.toResponse(studentRepository.findAll());
        return response;

    }

    @Override
    public void createStudent(StudentRequestDto studentDto) {
        Student student= studentMapper.toEntity(studentDto);
        studentRepository.save(student);


    }

    @Override
    public void deleteStudent(String studentId) {
        Optional<Student> optionalStudent=studentRepository.findByUserId(studentId);
        if(optionalStudent.isEmpty()){
            throw new APIException("User mot found");
        }
        Student student=optionalStudent.get();
        studentRepository.delete(student);
    }

    @Override
    public void updateStudent(StudentRequestDto updatedStudent,Long id){
        Student existingStudent=studentRepository.findById(id)
                .orElseThrow(()->new APIException(("Student not found")));

        studentMapper.updateEntityFromDto(updatedStudent,existingStudent);
        studentRepository.save(existingStudent);
    }

    @Override
    public StudentResponseDto getStudent(String studentId) {
        Optional<Student> optionalStudent=studentRepository.findByUserId(studentId);
        if(optionalStudent.isEmpty()){
            throw  new APIException("Student not found");
        }
        return  studentMapper.toResponse(optionalStudent.get());
    }

    @Override
    public List<StudentResponseDto> getStudentByFaculty(Faculty faculty) {
        List<Student> facultyStudents=studentRepository.findByFaculty(faculty);
        if(facultyStudents.isEmpty()){
            throw  new APIException("Student not found in the faculty");
        }
        return  studentMapper.toResponse(facultyStudents);
    }

    @Override
    public List<StudentResponseDto> getStudentByDepartment(Department department) {
        List<Student> departmentStudents=studentRepository.findByDepartment(department);
        if(departmentStudents.isEmpty()){
            throw  new APIException("Student not found in the department");
        }
        return  studentMapper.toResponse(departmentStudents);
    }

    @Override
    public List<StudentResponseDto> getStudentByAdvisor(String advisorId) {
        List<Student> advisorStudents=studentRepository.findByAdvisor(advisorId);
        if(advisorStudents.isEmpty()){
            throw  new APIException("Student not found in the department");
        }
        return  studentMapper.toResponse(advisorStudents);
    }

    @Override
    public List<StudentResponseDto> getStudentByStatus(StudentStatus status) {
        List<Student> statusStudents=studentRepository.findByStatus(status);
        if(statusStudents.isEmpty()){
            throw  new APIException("Student not found in this status");
        }
        return  studentMapper.toResponse(statusStudents);
    }
    @Override
    public List<StudentResponseDto> getStudentByCampus(Campus campus) {
        List<Student> campusStudents=studentRepository.findByCampus(campus);
        if(campusStudents.isEmpty()){
            throw  new APIException("Student not found in this campus");
        }
        return  studentMapper.toResponse(campusStudents);
    }
    @Override
    public List<StudentResponseDto> getStudentByYear(int year) {
        List<Student> yearStudents=studentRepository.findByYear(year);
        if(yearStudents.isEmpty()){
            throw  new APIException("Student not found in this campus");
        }
        return  studentMapper.toResponse(yearStudents);
    }




}
