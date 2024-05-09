package com.example.buensaborback.entities.dtos;

import com.example.buensaborback.entities.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDto extends BaseDto{
    private String auth0Id;
    private String username;
    private Rol rol;
}
