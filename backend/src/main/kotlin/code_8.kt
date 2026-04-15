package com.example.demo.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * MVC 컨텍스트에 맞는 MockMvc 를 이용한 테스트
 * WebTestClient 대신 사용하면 WebFlux 의존성 없이 테스트 가능
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class HelloControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `GET /api/hello should return hello message`() {
        mockMvc.perform(get("/api/hello"))
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Hello World"))
    }
}