package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.clearance.ClearanceRequestDto;
import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.models.Clearance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClearanceMapper {
    Clearance toEntity(StudentClearanceRequestDto dto);
    Clearance toEntity(ClearanceRequestDto dto);

    ClearanceResponseDto toResponse(Clearance clearance);
    List<ClearanceResponseDto> toResponse(List<Clearance> students);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(StudentClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);
}
