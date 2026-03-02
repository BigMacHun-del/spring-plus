package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;

    private Long requestUserId;

    private Long targetManagerUserId;

    private String status; // "SUCCESS" or "FAILURE"

    private String message;

    private LocalDateTime createdAt;

    public Log(Long todoId, Long requestUserId, Long targetManagerUserId, String status, String message) {
        this.todoId = todoId;
        this.requestUserId = requestUserId;
        this.targetManagerUserId = targetManagerUserId;
        this.status = status;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }
}