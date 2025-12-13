package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;
import com.bdu.clearance.models.Users;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Users toEntity(UserRequestDto dto);

    UserResponseDto toResponse(Users user);

    List<UserResponseDto> toResponse(List<Users> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserRequestDto userDto, @MappingTarget Users existingUser);
}