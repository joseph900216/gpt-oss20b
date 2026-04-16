package com.example.demo.controller

import com.example.demo.dto.UserRequestDto
import com.example.demo.dto.UserResponseDto
import com.example.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody dto: UserRequestDto): UserResponseDto =
        service.createUser(dto)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): UserResponseDto =
        service.getUserById(id)

    @GetMapping
    fun getAll(): List<UserResponseDto> =
        service.getAllUsers()

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody dto: UserRequestDto): UserResponseDto =
        service.updateUser(id, dto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = service.deleteUser(id)
}