package com.example.demo.service

import org.springframework.stereotype.Service

/**
 * 비즈니스 로직을 담당하는 서비스
 */
@Service
class HelloService {
    fun getHelloMessage(): String = "Hello World"
}