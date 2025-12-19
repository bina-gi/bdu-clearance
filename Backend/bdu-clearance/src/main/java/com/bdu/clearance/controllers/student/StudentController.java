package com.bdu.clearance.controllers.student;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.enums.StudentStatus;
import com.bdu.clearance.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // === CREATE ===
    @PostMapping("create")
    public ResponseEntity<Void> createStudent(@Valid @RequestBody StudentRequestDto studentDto) {
        studentService.createStudent(studentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @GetMapping("/advisor/{advisorId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentByAdvisor(@PathVariable Long advisorId) {
        return ResponseEntity.ok(studentService.getStudentByAdvisor(advisorId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<StudentResponseDto>> getStudentByStatus(@RequestParam StudentStatus status) {
        return ResponseEntity.ok(studentService.getStudentByStatus(status));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<StudentResponseDto>> getStudentByYearOfStudy(@PathVariable int year) {
        return ResponseEntity.ok(studentService.getStudentByYearOfStudy(year));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody StudentRequestDto studentDto, @PathVariable Long id) {
        studentService.updateStudent(studentDto, id);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
