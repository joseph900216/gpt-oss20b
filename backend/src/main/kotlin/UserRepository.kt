package com.example.demo.repository

import com.example.demo.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype even.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}