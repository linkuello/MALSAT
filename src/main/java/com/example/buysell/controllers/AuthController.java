package com.example.buysell.controllers;

import com.example.buysell.dto.AuthResponse;
import com.example.buysell.dto.LoginRequest;
import com.example.buysell.dto.RegisterRequest;
import com.example.buysell.models.User;
import com.example.buysell.repositories.UserRepository;
import com.example.buysell.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // Исправленный конструктор без emailService
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Email уже зарегистрирован!", null));
        }

        // Создаем нового пользователя без генерации токена подтверждения
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(com.example.buysell.models.Role.USER);
        user.setVerified(true); // Устанавливаем в true, чтобы пользователь был активирован сразу
        user.setConfirmationToken(null); // Убираем токен подтверждения

        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse("Пользователь зарегистрирован успешно!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Убираем проверку на подтверждение email
        // if (!user.isVerified()) {
        //     return ResponseEntity.badRequest().body(new AuthResponse("Email не подтверждён!", null));
        // }

        // Проверяем пароль перед аутентификацией
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Неверный email или пароль", null));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new AuthResponse("Успешный вход!", token));

        } catch (BadCredentialsException e) {
            logger.warn("Неудачная попытка входа для: {}", request.getEmail());
            return ResponseEntity.badRequest().body(new AuthResponse("Неверный email или пароль", null));
        }
    }
}
