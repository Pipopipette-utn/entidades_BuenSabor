package com.example.buensaborback.domain.dto;

import com.example.buensaborback.domain.enums.Rol;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsuarioDto extends BaseDto {
    String auth0Id;
    String username;
    String email;
    Rol rol;
}
