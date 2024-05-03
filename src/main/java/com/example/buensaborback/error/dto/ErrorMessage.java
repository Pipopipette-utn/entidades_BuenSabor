package com.example.buensaborback.error.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

/* Plantilla para retornar errores */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private HttpStatus status;
    private String message;

    // Ej: { status: NOT_FOUND, message: "No encontrado en la base de datos" }
}