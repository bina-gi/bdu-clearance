package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.clearance.StaffClearanceRequestDto;
import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClearanceMapper {
    Clearance toEntity(StudentClearanceRequestDto dto);
    Clearance toEntity(StaffClearanceRequestDto dto);

    ClearanceResponseDto toResponse(Clearance clearance);
    List<ClearanceResponseDto> toResponse(List<Clearance> students);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudentClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StaffClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);
}
