package com.example.buensaborback.config.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
     private String destinatario;
     private String asunto;
     private String mensaje;
}
