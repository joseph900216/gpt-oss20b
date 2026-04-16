package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserResponseDto;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserDto dto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, UserDto dto);

    void   deleteUser(Long id);
}