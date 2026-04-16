package com.example.demo.controller

import com.example.demo.dto.UserRequestDto
import com.example.demo.dto.UserResponseDto
import com.example.demo.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servletRequest

@WebMvcTest(UserController::class)
class UserControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean lateinit var service: UserService
    @Autowired lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("POST /api/users -> 201 CREATED")
    fun createUser() {
        val req = UserRequestDto("jane", "jane@ex.com")
        val resp = UserResponseDto(1, "jane", "jane@ex.com", java.time.LocalDateTime.now())
        whenever(service.createUser(req)).thenReturn(resp)

        mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value("jane"))
    }

    @Test
    @DisplayName("GET /api/users/{id} -> 200 OK")
    fun getUser() {
        val out = UserResponseDto(1, "jane", "jane@ex.com", java.time.LocalDateTime.now())
        whenever(service.getUserById(1)).thenReturn(out)

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("jane"))
    }

    @Test
    @DisplayName("DELETE /api/users/{id} -> 204 NO CONTENT")
    fun deleteUser() {
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent)
        verify(service).deleteUser(1)
    }
}