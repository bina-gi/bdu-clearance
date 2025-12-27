package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.property.PropertyRequestDto;
import com.bdu.clearance.dto.property.PropertyResponseDto;
import com.bdu.clearance.models.Property;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "issuedBy", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    @Mapping(target = "borrowStatus", ignore = true)
    @Mapping(target = "borrowDate", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "returnedTo", ignore = true)
    Property toEntity(PropertyRequestDto dto);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(target = "studentName", expression = "java(property.getStudent().getUser().getFirstName() + \" \" + property.getStudent().getUser().getMiddleName() + \" \" + property.getStudent().getUser().getLastName())")
    @Mapping(source = "issuedBy.id", target = "issuedByUserId")
    @Mapping(target = "issuedByName", expression = "java(property.getIssuedBy().getFirstName() + \" \" + property.getIssuedBy().getMiddleName() + \" \" + property.getIssuedBy().getLastName())")
    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    @Mapping(source = "organizationalUnit.organizationName", target = "organizationalUnitName")
    PropertyResponseDto toResponse(Property property);

    List<PropertyResponseDto> toResponse(List<Property> properties);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "issuedBy", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    @Mapping(target = "borrowStatus", ignore = true)
    @Mapping(target = "borrowDate", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "returnedTo", ignore = true)
    void updateEntityFromDto(PropertyRequestDto dto, @MappingTarget Property entity);
}
