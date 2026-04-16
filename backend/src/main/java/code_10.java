package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional   // 단일 트랜잭션(데이터 변조)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDto createUser(UserDto dto) {
        User entity = new User(dto.getName(), dto.getEmail());
        User created = repository.save(entity);
        return mapToResponse(created);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        User updated = repository.save(user);
        return mapToResponse(updated);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private UserResponseDto mapToResponse(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }
}