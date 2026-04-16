package com.example.demo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequestDto(
    @field:NotBlank(message = "username은 필수입니다.")
    val username: String,
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String
)