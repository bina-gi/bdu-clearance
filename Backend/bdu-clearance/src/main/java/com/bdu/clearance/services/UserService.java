package com.bdu.clearance.services;

import java.util.List;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;


public interface UserService {

    //Create
    void createUser(UserRequestDto newUser);

    //Get
     List<UserResponseDto> getAllUsers();
     UserResponseDto getUserById(String userId);
     List<UserResponseDto> getUsersByStatus(Boolean status);

    //Delete And Update
     void deleteUser(String userId);
     void updateUser(UserRequestDto user, Long id);

    //other
     void resetPassword(String userId);

//     List<UserResponseDto> getUsersByCampus(Campus campus);
//     List<UserResponseDto> getUsersByRole(UserRole role);

}
