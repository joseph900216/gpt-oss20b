package com.example.demo.dto

import java.time.LocalDateTime

data class UserResponseDto(
    val id: Long,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime
)