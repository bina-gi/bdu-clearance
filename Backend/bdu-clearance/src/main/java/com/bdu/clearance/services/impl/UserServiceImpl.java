package com.bdu.clearance.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bdu.clearance.dto.user.UserRequestDto;
import com.bdu.clearance.dto.user.UserResponseDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.UserMapper;
import com.bdu.clearance.models.OrganizationalUnit;
import com.bdu.clearance.models.Role;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.repositories.RoleRepository;
import com.bdu.clearance.repositories.UserRepository;
import com.bdu.clearance.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void createUser(UserRequestDto newUser) {

        Role role = roleRepository
                .findById(newUser.getRoleId())
                .orElseThrow(() -> new APIException("Role not found with id: " + newUser.getRoleId()));

        OrganizationalUnit organizationalUnit = organizationalUnitRepository
                .findById(newUser.getOrganizationalUnitId())
                .orElseThrow(() -> new APIException(
                        "Organizational Unit not found with id: " + newUser.getOrganizationalUnitId()));

        Users user = userMapper.toEntity(newUser);
        user.setUserRole(role);
        user.setOrganizationalUnit(organizationalUnit);

        String rawPassword = user.getLastName() + user.getUserId();
        user.setPassword(passwordEncoder.encode(rawPassword));

        // System.out.println("Role: " + user.getUserRole());
        // System.out.println("OrgUnit: " + user.getOrganizationalUnit());

        if (user.getIsActive() == null) {
            user.setIsActive(true);
        }

        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<Users> allUsers = userRepository.findAll();
        return userMapper.toResponse(allUsers);
    }

    @Override
    public UserResponseDto getUserById(String userId) {
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new APIException("User not found with Id: " + userId));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponseDto> getUsersByStatus(Boolean isActive) {
        List<Users> statusUsers = userRepository.findByIsActive(isActive);
        return userMapper.toResponse(statusUsers);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new APIException("User not found with Id: " + userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(UserRequestDto userDto, Long id) {

        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new APIException("User not found with Id: " + id));

        userMapper.updateEntityFromDto(userDto, existingUser);

        userRepository.save(existingUser);
    }

    @Override
    public void resetPassword(String userId) {
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new APIException("User not found with Id: " + userId));
        String encodedPassword = passwordEncoder.encode(user.getLastName() + user.getUserId());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    // @Override
    // public List<UserResponseDto> getUsersByRole(UserRole role){
    // List<Users> roleUsers=userRepository.findByRole(role);
    // if(roleUsers.isEmpty()){
    // throw new APIException("Campus not found with the Campus Name "+role);
    // }
    // return userMapper.toResponse(roleUsers);
    // }
    // @Override
    //
    // public List<UserResponseDto> getUsersByCampus(Campus campus){
    // List<Users> campusUsers=userRepository.findByCampus(campus);
    //
    // if(campusUsers.isEmpty()){
    // throw new APIException("Campus not found with the Campus Name "+campus);
    // }
    // return userMapper.toResponse(campusUsers);
    // }
}
