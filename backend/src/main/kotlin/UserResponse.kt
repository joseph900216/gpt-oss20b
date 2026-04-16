package com.example.demo.dto

import com.example.demo.domain.User

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            age = user.age
        )
    }
}