package com.example.demo

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User @JvmOverloads constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var username: String,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)