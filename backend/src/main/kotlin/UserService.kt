package com.example.service

import com.example.domain.User
import com.example.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun signUp(name: String, email: String): Long {
        // 중복 체크 로직 등 추가 가능
        val user = User(name = name, email = email)
        return userRepository.save(user).id ?: 0L
    }

    @Transactional
    fun updateUserInfo(userId: Long, newName: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }
        user.updateName(newName)
    }
}