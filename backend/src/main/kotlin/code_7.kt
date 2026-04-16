package com.example.demo.service

import com.example.demo.dto.UserRequestDto
import com.example.demo.dto.UserResponseDto

interface UserService {
    fun createUser(dto: UserRequestDto): UserResponseDto
    fun getUserById(id: Long): UserResponseDto
    fun getAllUsers(): List<UserResponseDto>
    fun updateUser(id: Long, dto: UserRequestDto): UserResponseDto
    fun deleteUser(id: Long)
}