package com.bdu.clearance.services;

import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService{

    private Long nextId=1L;
    @Autowired
    private StudentRepository studentRepository;



    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public void createStudent(Student student) {
        student.setId(nextId);
        studentRepository.save(student);
    }

    @Override
    public String deleteStudent(String studentId) {
        List<Student> allStudents=studentRepository.findAll();
        Student student=allStudents.stream().filter(s->s.getStudentId() !=null && s.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Student with id:"+studentId+" not found"));
        studentRepository.delete(student);
        return "Clearance with ID: "+ studentId +" deleted Successfully!";
    }

    @Override
    public Student updateStudent(Student updatedStudentData,Long id){
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + id + " not found.");
        }
            Student existingStudent = studentOptional.get();

        existingStudent.setStudentId(updatedStudentData.getStudentId());
        existingStudent.setFirstName(updatedStudentData.getFirstName());
        existingStudent.setMiddleName(updatedStudentData.getMiddleName());
        existingStudent.setLastName(updatedStudentData.getLastName());
        existingStudent.setInstitute(updatedStudentData.getInstitute());
        existingStudent.setFaculty(updatedStudentData.getFaculty());
        existingStudent.setAdvisor(updatedStudentData.getAdvisor());
        existingStudent.setYear(updatedStudentData.getYear());
        existingStudent.setIsGraduated(updatedStudentData.getIsGraduated());

        return studentRepository.save(existingStudent);
    }

    @Override
    public Student getStudent(String studentId) {
        List<Student> allStudents=studentRepository.findAll();
        Optional<Student> optionalStudent=allStudents.stream().filter(s->s.getStudentId()!=null && s.getStudentId().equals(studentId))
                .findFirst();
        if(optionalStudent.isPresent()){
            Student foundStudent=optionalStudent.get();
            return foundStudent;
    }else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"0 student found with the id:"+studentId);
        }
    }
    @Override
    public List<Student> getStudentByFaculty(String faculty) {
        List<Student> allStudents=studentRepository.findAll();
        List<Student> foundStudents = allStudents.stream()
                .filter(s -> s.getFaculty() != null && s.getFaculty().equalsIgnoreCase(faculty))
                .collect(Collectors.toList());

        if(!foundStudents.isEmpty()){
            return foundStudents;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"0 student found in the faculty of: "+faculty);
        }
    }


    @Override
    public List<Student> getStudentByDepartment(String department) {
        List<Student> allStudents=studentRepository.findAll();
        List<Student> foundStudents = allStudents.stream()
                .filter(s -> s.getFaculty() != null && s.getFaculty().equalsIgnoreCase(department))
                .collect(Collectors.toList());

        if(!foundStudents.isEmpty()){
            return foundStudents;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"0 student found in the department of: "+department);
        }
    }

    @Override
    public List<Student> getStudentByAdvisor(String advisor) {
        List<Student> allStudents=studentRepository.findAll();
        List<Student> foundStudents = allStudents.stream()
                .filter(s -> s.getFaculty() != null && s.getFaculty().equalsIgnoreCase(advisor))
                .collect(Collectors.toList());

        if(!foundStudents.isEmpty()){
            return foundStudents;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"0 student found in the faculty of: "+advisor);
        }
    }
}
