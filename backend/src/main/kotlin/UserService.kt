package com.example.service

import com.example.domain.User
import com.example.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun getUser(id: Long): User = userRepository.findById(id)
        .orElseThrow { NoSuchElementException("User not found with id: $id") }

    @Transactional
    fun createUser(name: String, email: String): User {
        val newUser = User(name = name, email = email)
        return userRepository.save(newUser)
    }

    @Transactional
    fun updateUser(id: Long, name: String): User {
        val user = getUser(id)
        val updatedUser = User(id = user.id, name = name, email = user.email)
        return userRepository.save(updatedUser)
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user = getUser(id)
        userRepository.delete(user)
    }
}