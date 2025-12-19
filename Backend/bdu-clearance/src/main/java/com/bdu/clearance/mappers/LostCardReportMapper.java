package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.report.ReportRequestDto;
import com.bdu.clearance.dto.report.ReportResponseDto;
import com.bdu.clearance.models.LostCardReport;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LostCardReportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true) // Set in Service
    LostCardReport toEntity(ReportRequestDto dto);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(target = "studentName", expression = "java(report.getStudent().getUser().getFirstName() + \" \" + report.getStudent().getUser().getMiddleName() + \" \" + report.getStudent().getUser().getLastName())")
    ReportResponseDto toResponse(LostCardReport report);

    List<ReportResponseDto> toResponse(List<LostCardReport> reports);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(ReportRequestDto dto, @MappingTarget LostCardReport entity);
}
