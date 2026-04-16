package com.example.controller

import com.example.domain.User
import com.example.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request.name, request.email)
        return ResponseEntity(user.toResponse(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUser(id).toResponse())
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id:Long, @RequestBody request: UserUpdate): ResponseEntity<UserResponse> {
        val user = userService.updateUser(id, request.name)
        return ResponseEntity.ok(user.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    // Helper DTOs
    data class UserRequest(val name: String, val email: String)
    data class UserUpdate(val name: String)
}

// Extension function for mapping
fun User.toResponse() = UserResponse(id!!, name, email)