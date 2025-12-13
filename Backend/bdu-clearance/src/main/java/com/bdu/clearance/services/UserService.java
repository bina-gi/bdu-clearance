package com.bdu.clearance.services;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;
import com.bdu.clearance.enums.Campus;
import com.bdu.clearance.enums.UserRole;

import java.util.List;


public interface UserService {

    //Create
    void createUser(UserRequestDto newUser);


    //Get
     List<UserResponseDto> getAllUsers();
     List<UserResponseDto> getUsersByRole(UserRole role);
     UserResponseDto getUserById(String userId);
     List<UserResponseDto> getUsersByCampus(Campus campus);
     List<UserResponseDto> getUsersByStatus(Boolean status);

    //Delete And Update
     void deleteUser(String userId);
     void updateUser(UserRequestDto user, Long id);

    //other
     void resetPassword(String userId);


}
