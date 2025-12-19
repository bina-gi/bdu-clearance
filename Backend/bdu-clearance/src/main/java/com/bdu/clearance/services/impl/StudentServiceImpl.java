package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.enums.StudentStatus;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.StudentMapper;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final com.bdu.clearance.repositories.UserRepository userRepository;

    @Override
    public List<StudentResponseDto> getAllStudents() {
        List<StudentResponseDto> response = studentMapper.toResponse(studentRepository.findAll());
        return response;

    }

    @Override
    public void createStudent(StudentRequestDto studentDto) {
        if (studentRepository.findByUserUserId(String.valueOf(studentDto.getUserId())).isPresent()) {

            throw new APIException("Student profile already exists for this user");
        }

        Student student = studentMapper.toEntity(studentDto);

        // Fetch User
        Users user = userRepository.findById(studentDto.getUserId())
                .orElseThrow(() -> new APIException("User not found with id: " + studentDto.getUserId()));
        student.setUser(user);

        // Fetch Advisor if present
        if (studentDto.getAdvisorId() != null) {
            com.bdu.clearance.models.Users advisor = userRepository.findById(studentDto.getAdvisorId())
                    .orElseThrow(() -> new APIException("Advisor not found with id: " + studentDto.getAdvisorId()));
            student.setAdvisor(advisor);
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        Optional<Student> optionalStudent = studentRepository.findByUserUserId(studentId);
        if (optionalStudent.isEmpty()) {
            throw new APIException("Student not found");
        }
        Student student = optionalStudent.get();
        studentRepository.delete(student);
    }

    @Override
    public void updateStudent(StudentRequestDto updatedStudent, Long id) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new APIException(("Student not found")));

        // Update basic fields
        studentMapper.updateEntityFromDto(updatedStudent, existingStudent);

        // Update Advisor if changed
        if (updatedStudent.getAdvisorId() != null) {
            // Check if advisor is different or new
            if (existingStudent.getAdvisor() == null
                    || !existingStudent.getAdvisor().getId().equals(updatedStudent.getAdvisorId())) {
                Users advisor = userRepository.findById(updatedStudent.getAdvisorId())
                        .orElseThrow(
                                () -> new APIException("Advisor not found with id: " + updatedStudent.getAdvisorId()));
                existingStudent.setAdvisor(advisor);
            }
        } else {
            // If advisorId is null in DTO, should we remove the advisor?
            // Assuming yes for flexibility
            existingStudent.setAdvisor(null);
        }

        studentRepository.save(existingStudent);
    }

    @Override
    public StudentResponseDto getStudent(String studentId) {
        Optional<Student> optionalStudent = studentRepository.findByUserUserId(studentId);
        if (optionalStudent.isEmpty()) {
            throw new APIException("Student not found");
        }
        return studentMapper.toResponse(optionalStudent.get());
    }

    @Override
    public List<StudentResponseDto> getStudentByAdvisor(Long advisorId) {
        List<Student> advisorStudents = studentRepository.findByAdvisorId(advisorId);
        if (advisorStudents.isEmpty()) {
            throw new APIException("No students found for this advisor");
        }
        return studentMapper.toResponse(advisorStudents);
    }

    @Override
    public List<StudentResponseDto> getStudentByStatus(StudentStatus status) {
        List<Student> statusStudents = studentRepository.findByStudentStatus(status);
        if (statusStudents.isEmpty()) {
            throw new APIException("Student not found in this status");
        }
        return studentMapper.toResponse(statusStudents);
    }

    @Override
    public List<StudentResponseDto> getStudentByYearOfStudy(int year) {
        List<Student> yearStudents = studentRepository.findByYearOfStudy(year);
        if (yearStudents.isEmpty()) {
            throw new APIException("No students found for year " + year);
        }
        return studentMapper.toResponse(yearStudents);
    }

}