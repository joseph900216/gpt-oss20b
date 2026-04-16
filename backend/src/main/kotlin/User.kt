package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import jakarta.persistence.*
import org.springframework.transaction.annotation.Transactional

// ==========================================
// 1. DOMAIN LAYER (Model)
// ==========================================
@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String
)

// ==========================================
// 2. REPOSITORY LAYER
// ==========================================
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}

// ================================= даже
// 3. DTO LAYER (Data Transfer Objects)
// ==========================================
data class UserRequest(val name: String, val email: String)
data class UserResponse(val id: Long, val name: String, val email: String)

// ==========================================
// 4. SERVICE LAYER (Business Logic)
// ==========================================
@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("User not found") }
    }

    @Transactional
    fun createUser(request: UserRequest): User {
        if (userRepository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }
        val newUser = User(name = request.name, email = request.email)
        return userRepository.save(newUser)
    }

    @Transactional
    fun updateUser(id: Long, request: UserRequest): User {
        val existingUser = getUser(id)
        val updatedUser = User(id = existingUser.id, name = request.name, email = request.email)
        return userRepository.save(updatedUser)
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user = getUser(id)
        userRepository.delete(user)
    }
}

// ==========================================
// 5. CONTROLLER LAYER (API Endpoints)
// ==========================================
@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userService.getUser(id)
        return ResponseEntity.ok(user.toResponse())
    }

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse())
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.updateUser(id, request)
        return ResponseEntity.ok(user.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    // Extension function to map Entity to DTO
    private fun User.toResponse() = UserResponse(id!!, name, email)
}

// ==========================================
// 6. EXCEPTION HANDLING (Global)
// ==========================================
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)

    @ExceptionHandler(Exception::class)
    fun handleGeneral(ex: Exception): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred")
}

// ==========================================
// 7. APPLICATION LAUNCH
// ==========================================
@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}