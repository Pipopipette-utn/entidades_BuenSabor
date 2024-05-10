package com.example.buensaborback.entities.dtos;

import com.example.buensaborback.entities.Persona;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDto extends PersonaDto {

    private Set<PedidoDto> pedidos = new HashSet<>();

}
