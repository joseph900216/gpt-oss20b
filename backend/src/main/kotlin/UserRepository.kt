package com.example.repository

import com.example.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework난.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}