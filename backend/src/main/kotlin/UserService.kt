package com.example.demo.service

import com.example.demo.domain.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): User = userRepository.findById(id
        .orElseThrow { NoSuchElementException("User with ID $id not found") }

    fun create(name: String, email: String): User {
        val user = User(name = name, email = email)
        return userRepository.save(user)
    }

    fun update(id: Long, name: String, email: String): User {
        val user = findById(id)
        user.name = name
        user.email = email
        return userRepository.save(user)
    }

    fun delete(id: Long) {
        val user = findById(id)
        userRepository.delete(user)
    }
}