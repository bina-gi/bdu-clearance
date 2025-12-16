package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;
import com.bdu.clearance.models.Users;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "advisedStudents", ignore = true)
    @Mapping(target = "issuedProperties", ignore = true)
    @Mapping(target = "approvedClearances", ignore = true)
    @Mapping(target = "password", ignore = true)
    Users toEntity(UserRequestDto dto);

    UserResponseDto toResponse(Users user);

    List<UserResponseDto> toResponse(List<Users> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    void updateEntityFromDto(UserRequestDto userDto, @MappingTarget Users existingUser);
}