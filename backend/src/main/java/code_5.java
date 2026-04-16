package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "`user`")   // 테이블 이름이 SQL 예약어인 경우 백틱 필요
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    /* 기본생성자 (Spring Data JPA가 필요) */
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /* Getters & Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}