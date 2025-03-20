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
    private LocalDateTime timestamp = LocalDateTime.now();

    // Геттеры и сеттеры
}
