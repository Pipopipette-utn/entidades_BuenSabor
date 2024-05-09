package com.example.buensaborback.entities.dtos;

import lombok.Builder;

@Builder
public class ErrorDto {
    private String errorMsg;
    private String errorClass;
}
