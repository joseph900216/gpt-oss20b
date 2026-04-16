package com.example.user.dto

/**
 * Request DTOs for incoming API calls
 */
data class UserCreateRequest(val name: String, val email: String)
data class UserUpdateRequest(val name: String, val email: String)

/**
 * Response DTO for outgoing API calls
 */
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
) {
    companion object {
        fun from(user: com.example.user.domain.User): UserResponse {
            return UserResponse(user.id!!, user.name, user.email)
        }
    }
}