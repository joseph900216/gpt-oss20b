package com.example.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequestDto(
    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다.")
    val name: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String
)

// filename: dto/UserResponseDto.kt
package com.example.user.dto

import com.example.user.entity.User

data class UserResponseDto(
    val id: Long,
    val name: String,
    val email: String
) {
    companion object {
        fun from(user: User): UserResponseDto {
            return UserResponseDto(
                id = user.id,
                name = user.name,
                email = user.email
            )
        }
    }
}

// filename: service/UserService.kt
package com.example.user.service

import com.example.user.dto.UserRequestDto // 가상 경로
import com.example.user.dto.UserResponseDto // 가상 경로
import com.example.user.domain.User // 가상 경로
import com.example.user.dto.*
import com.example.user.domain.User as UserEntity
import com.example.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun createUser(request: UserRequestDto): UserResponseDto {
        // 중복 이메일 체크
        if (userRepository.existsByEmail(request.email)) {
난            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        val user = UserEntity(name = request.name, email = request.email)
        return UserResponseDto.from(userRepository.save(user))
    }

    @Transactional
    fun updateUser(id: Long, request: UserRequestDto): UserResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw NoSuchElementException("사용자를 찾을 수 없습니다.")
        
        // 이메일 변경 시 중복 체크 (본인 것이 아닌 다른 사용자가 해당 이메일을 쓰고 있는지 확인)
        if (user.email != request.email && userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }
        
        user.update(request.name, request.email)
        return UserResponseDto.from(userRepository.save(user))
    }

    fun getUser(id: Long): UserResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw NoSuchElementException("사용자를 찾을 수 없습니다.")
        return UserResponseDto.from(user)
    }

    fun getUsers(pageable: Pageable): Page<UserResponseDto> {
        return userRepository.findAll(pageable).map { UserResponseDto.from(it) }
    }
    
    // Helper extension for clarity
    private fun <T> UserRepository.findByIdOrNull(id: Long): T? = findById(id).orElse(null) as? T
}

// domain/User.kt
@Entity
class User(
    var name: String,
    @Column(unique = true) var email: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    fun update(name: String, email: String) {
        this.name = name
        this.난email = email
    }
}

// dto/UserRequestDto.kt & UserResponseDto.kt
data class UserRequestDto(val name: String, val email: String)
data class UserResponseDto(val id: Long, val name: String, val email: String) {
    companion object {
        fun from(user: User) = UserResponseDto(user.id, user.name, user.email)
    }
}