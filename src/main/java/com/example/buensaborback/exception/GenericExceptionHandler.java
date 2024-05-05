package com.example.buensaborback.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GenericExceptionHandler extends RuntimeException {
    private String code;
    private HttpStatus status;

    public GenericExceptionHandler(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
