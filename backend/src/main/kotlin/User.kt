package com.example.userapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String
) {
    fun updateName(newName: String) {
        this.name = newName
    }

    fun updateEmail(newEmail: String) {
        this.email = newEmail
    }
}

// filename: UserRepository.kt
package com.example.userapi.repository

import com.example.userapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}