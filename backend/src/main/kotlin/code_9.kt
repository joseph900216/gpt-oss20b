package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
}