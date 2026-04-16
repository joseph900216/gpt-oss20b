package com.example.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User @PersistenceConstructor constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false, unique = true)
    val email: String
) {
    // 비즈니스 로직: 이름 변경
    fun updateName(newName: String) {
        require(newName.isNotBlank()) { "이름은 빈 값일 수 없습니다." }
        this.name = newName
    }
}