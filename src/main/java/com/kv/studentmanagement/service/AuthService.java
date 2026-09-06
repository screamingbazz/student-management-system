package com.kv.studentmanagement.service;

import com.kv.studentmanagement.entity.User;
import com.kv.studentmanagement.repository.UserRepository;
import com.kv.studentmanagement.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new student user securely.
     */
    public String registerStudentUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("ROLE_STUDENT");

        userRepository.save(user);

        return "Student registered successfully inside SQLite database!";
    }

    /**
     * Authenticates a user and issues a stateless JWT Token.
     */
    public String loginUser(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(authToken);

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String jwtToken =
                jwtService.generateToken(userDetails);

        return jwtToken;
    }
}
