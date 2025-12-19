package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.student.StudentRequestDto;
import com.bdu.clearance.dto.student.StudentResponseDto;
import com.bdu.clearance.models.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "advisor", ignore = true)
    @Mapping(target = "clearances", ignore = true)
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "lostCardReports", ignore = true)
    Student toEntity(StudentRequestDto dto);

    @Mapping(source = "user.userId", target = "studentId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "fullName", expression = "java(student.getUser().getFirstName() + \" \" + student.getUser().getMiddleName() + \" \" + student.getUser().getLastName())")
    @Mapping(source = "advisor.id", target = "advisorId")
    @Mapping(target = "advisorName", expression = "java(student.getAdvisor() != null ? student.getAdvisor().getFirstName() + \" \" + student.getAdvisor().getMiddleName() + \" \" + student.getAdvisor().getLastName() : null)")
    @Mapping(target = "clearanceCount", expression = "java(student.getClearances() != null ? student.getClearances().size() : 0)")
    @Mapping(target = "propertyCount", expression = "java(student.getProperties() != null ? student.getProperties().size() : 0)")
    @Mapping(target = "lostCardReportCount", expression = "java(student.getLostCardReports() != null ? student.getLostCardReports().size() : 0)")
    StudentResponseDto toResponse(Student student);

    List<StudentResponseDto> toResponse(List<Student> students);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "advisor", ignore = true)
    @Mapping(target = "clearances", ignore = true)
    @Mapping(target = "properties", ignore = true)
    @Mapping(target = "lostCardReports", ignore = true)
    void updateEntityFromDto(StudentRequestDto studentDto, @MappingTarget Student existingStudent);
}
