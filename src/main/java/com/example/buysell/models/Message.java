package com.example.buysell.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String sender;
    private String recipient;
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    // Устанавливаем временную метку перед сохранением
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();  // Устанавливаем текущее время перед сохранением
    }

    // Геттеры и сеттеры будут автоматически сгенерированы благодаря Lombok
}
