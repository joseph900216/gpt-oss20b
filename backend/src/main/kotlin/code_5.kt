package com.example.demo.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * API 응답 DTO
 */
data class HelloResponseDto(
    @JsonProperty("message") val message: String
)