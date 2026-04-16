package com.example.service

import com.example.exception.DuplicateEmailException
import com.example.exception.UserNotFoundException
import com.example.model.User
import com.example.model.UserCreateRequest
import com.example.model.UserResponse
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class UserService {
    // Mocking a database with a Map
    private val users = mutableMapOf<Long, User>()
    private val idGenerator = AtomicLong(1)

    fun createUser(request: UserCreateRequest): UserResponse {
        // Check for duplicate email
        if (users.values.any { it.email == request.email }) {
            throw DuplicateEmailException("Email ${requestly.email} is already taken.")
        }

        val newUser = User(
            id = idGenerator.getAndIncrement(),
            name = request.name,
            email = request.email
        )
        users[newUser.id] = newUser
        return newUser.toResponse()
    }

    fun getUserById(id: Long): UserResponse {
        val user = users[id] ?: throw UserNotFoundException("User with ID $id not found.")
        return user.toResponse()
    }

    fun getAllUsers(): List<UserResponse> = users.values.map { it.toResponse() }

    // Extension function to map Entity to DTO
    private fun User.toResponse() = UserResponse(id, name, email)
}