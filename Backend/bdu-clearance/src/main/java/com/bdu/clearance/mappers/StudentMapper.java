package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.models.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentRequestDto dto);
    StudentResponseDto toResponse(Student student);

    List<StudentResponseDto> toResponse(List<Student> students);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudentRequestDto studentDto, @MappingTarget Student existingStudent);
}
