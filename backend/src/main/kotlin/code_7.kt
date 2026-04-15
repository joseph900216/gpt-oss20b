package com.example.demo.controller

import com.example.demo.dto.HelloResponseDto
import com.example.demo.service.HelloService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * API 엔드포인트
 * - `/api/hello` 로 GET 요청을 받음
 * - 예외 발생 시 HTTP 500 을 반환하도록 예외 핸들링을 추가할 수 있음
 */
@RestController
@RequestMapping("/api")
class HelloController(private val helloService: HelloService) {

    @GetMapping("/hello")
    fun getHello(): ResponseEntity<HelloResponseDto> {
        return try {
            val message = helloService.getHelloMessage()
            ResponseEntity.ok(HelloResponseDto(message))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HelloResponseDto("Failed to get hello message"))
        }
    }
}