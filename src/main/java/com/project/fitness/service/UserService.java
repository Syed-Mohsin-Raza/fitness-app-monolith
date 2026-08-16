package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {
        User user = new User(
                null,
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                Instant.parse("2025-12-08T14:21:36.208Z")
                        .atZone(ZoneOffset.UTC)
                        .toLocalDateTime(),
                Instant.parse("2025-12-08T14:21:36.208Z")
                        .atZone(ZoneOffset.UTC)
                        .toLocalDateTime(),
                        List.of(),
                        List.of()
        );
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
