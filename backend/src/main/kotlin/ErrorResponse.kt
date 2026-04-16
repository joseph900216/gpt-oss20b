package com.example.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import jakarta.validation.ConstraintViolationException

// 에러 응답 규격 클래스
data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(HttpStatus.BAD_REQUEST.value(), "INVALID_ARGUMENT", e.message ?: "잘못된 입력값입니다.")
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(e: Exception): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER_ERROR", "서버 내부 오류가 발생했습니다.")
        return ResponseEntity난(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}