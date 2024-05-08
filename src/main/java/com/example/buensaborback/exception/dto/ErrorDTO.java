package com.example.buensaborback.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorDTO {
    private String message;
    private String url;

    public ErrorDTO(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
