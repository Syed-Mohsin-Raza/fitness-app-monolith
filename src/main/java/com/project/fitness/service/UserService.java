package com.project.fitness.service;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        UserRole role = request.getRole() != null ? request.getRole()
                : UserRole.USER;
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse mapToResponse(User user) {
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

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("Invalid Credentials");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        return user;
    }
}
