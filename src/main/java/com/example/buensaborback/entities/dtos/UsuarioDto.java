package com.example.buensaborback.entities.dtos;

import com.example.buensaborback.entities.enums.Rol;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioDto extends BaseDto{
    private String auth0Id;
    private String username;
    private Rol rol;
}
