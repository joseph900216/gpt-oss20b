package com.example.user.service

import com.example.user.domain.User
import com.example.user.dto.UserCreateRequest
import com.example.user.dto.UserResponse
import com.example.user.dto.UserUpdateRequest
import com.example.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(request: UserCreateRequest): UserResponse {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        val user = User(name = request.name, email = request.email)
        return UserResponse.from(userRepository.save(user))
    }

    fun getUser(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
        return UserResponse.from(user)
    }

    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.from(it) }
    }

    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id).orElseThrow { NoSuchranException("User not found") }
        user.update(request.name, request.email)
        return UserResponse.from(userRepository.save(user))
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) throw NoSuchElementException("User not found")
        userRepository.deleteById(id)
    }
    
    // Custom exception handler helper
    private class NoSuchranException(message: String) : RuntimeException(message)
}