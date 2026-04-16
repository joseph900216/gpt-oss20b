package com.example.demo.service

import com.example.demo.dto.UserRequestDto
import com.example.demo.dto.UserResponseDto
import com.example.demo.exception.UserNotFoundException
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val repository: UserRepository
) : UserService {

    override fun createUser(dto: UserRequestDto): UserResponseDto {
        if (repository.existsByUsername(dto.username))
            throw IllegalArgumentException("이미 존재하는 username입니다.")
        val user = User(username = dto.username, email = dto.email)
        return user.toResponseDto(repository.save(user))
    }

    override fun getUserById(id: Long): UserResponseDto =
        repository.findById(id).orElseThrow { UserNotFoundException(id) }.toResponseDto()

    override fun getAllUsers(): List<UserResponseDto> =
        repository.findAll().map { it.toResponseDto() }

    override fun updateUser(id: Long, dto: UserRequestDto): UserResponseDto {
        val user = repository.findById(id).orElseThrow { UserNotFoundException(id) }
        if (!user.username.equals(dto.username, true)) {
            repository.findByUsername(dto.username)?.let {
                if (it.id != id) throw IllegalArgumentException("중복된 username입니다.")
            }
        }
        user.username = dto.username
        user.email = dto.email
        return user.toResponseDto(repository.save(user))
    }

    override fun deleteUser(id: Long) {
        if (!repository.existsById(id))
            throw UserNotFoundException(id)
        repository.deleteById(id)
    }

    private fun User.toResponseDto() = UserResponseDto(id!!, username, email, createdAt)
}