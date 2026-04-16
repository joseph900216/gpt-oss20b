package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    public UserController(UserService service) { this.service = service; }

    /* CREATE */
    @PostMapping
    public ResponseEntity<UserResponseDto> create(
            @Valid @RequestBody UserDto dto) {

        UserResponseDto created = service.createUser(dto);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + created.getId()))
                .body(created);
    }

    /* READ ALL */
    @GetMapping
    public List<UserResponseDto> findAll() {
        return service.getAllUsers();
    }

    /* READ ONE */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        UserResponseDto user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /* UPDATE */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto dto) {

        UserResponseDto updated = service.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    /* DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();   // 204
    }
}