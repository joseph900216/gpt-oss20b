package com.example.model

/**
 * Represents the core entity. In a real app, this would be a JPA @Entity.
 */
data class User(
    val id: Long,
    val name: String,
    val email: String
)

/**
 * DTO for creating a user.
 */
data class UserCreateRequest(
    val name: String,
    val email: String
)

/**
 * DTO for returning user data to the client.
 */
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)