package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 기본 스프링 부트 애플리케이션 클래스
 * 스프링이 클래스의 프로퍼티에 접근할 수 있도록 open 으로 선언
 */
@SpringBootApplication
open class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}