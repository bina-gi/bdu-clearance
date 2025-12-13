package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;
import com.bdu.clearance.enums.Campus;
import com.bdu.clearance.enums.UserRole;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.UserMapper;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.UserRepository;
import com.bdu.clearance.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createUser(UserRequestDto newUser){
        Users user=userMapper.toEntity(newUser);
        user.setPassword(newUser.getLastName()+newUser.getUserId());
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        List<Users> allUsers=userRepository.findAll();
        List<UserResponseDto> response = userMapper.toResponse(allUsers);
        return response;
    }
    @Override

    public List<UserResponseDto> getUsersByRole(UserRole role){
        List<Users> roleUsers=userRepository.findByRole(role);
        if(roleUsers.isEmpty()){
            throw new APIException("Campus not found with the Campus Name "+role);
        }
        return userMapper.toResponse(roleUsers);
    }
    @Override
    public UserResponseDto getUserById(String userId){
        Optional<Users> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new APIException("User not found with Id: "+userId);
        }
        Users user=optionalUser.get();
        return  userMapper.toResponse(user);
    }
    @Override

    public List<UserResponseDto> getUsersByCampus(Campus campus){
        List<Users> campusUsers=userRepository.findByCampus(campus);

        if(campusUsers.isEmpty()){
            throw new APIException("Campus not found with the Campus Name "+campus);
        }
        return userMapper.toResponse(campusUsers);
    }

    @Override
    public List<UserResponseDto> getUsersByStatus(Boolean isActive){
        List<Users> statusUsers=userRepository.findByIsActive(isActive);
        if(statusUsers.isEmpty()){
            throw new APIException("Users not found");
        }
        return userMapper.toResponse(statusUsers);
    }
    @Override
    @Transactional
    public void deleteUser(String userId){
        Optional<Users> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw  new APIException("User with id:"+userId+" not found");
        }
        Users user=optionalUser.get();
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(UserRequestDto userDto, Long id){

        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new APIException("User not found with Id: " + id));

        userMapper.updateEntityFromDto(userDto, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void resetPassword(String userId){
        Optional<Users> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new APIException("User with id:"+userId+" not found");
        }
        Users user=optionalUser.get();
        user.setPassword(user.getLastName()+user.getUserId());
        userRepository.save(user);
    }
}
