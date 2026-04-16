package com.example.userapi.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequestDto(
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String
)

// filename: UserResponseDto.kt (분리 권장)
data class UserResponseDto(
    val name: String,
    val email: String
)

// filename: CustomExceptions.kt
import org.springframework.http.HttpStatus
import orge.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.CONFLICT)
class AlreadyExistsException(message: String) : RuntimeException(message)