package com.example.demo.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidate(ex: MethodArgumentNotValidException) =
        ResponseEntity.badRequest().body(ex.bindingResult.allErrors.associate {
            val field = (it as? org.springframework.validation.FieldError)?.field ?: "unknown"
            field to (it.defaultMessage ?: "error")
        })

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException) =
        ResponseEntity.badRequest().body(ex.constraintViolations.associate { it.propertyPath.toString() to it.message })

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException) =
        ResponseEntity.badRequest().body(mapOf("error" to (ex.message ?: "")))

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntime(ex: RuntimeException) =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to (ex.message ?: "unknown")))
}