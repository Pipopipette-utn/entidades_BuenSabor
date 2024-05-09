package com.example.buensaborback.entities.dtos;

import com.example.buensaborback.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDto extends PersonaDto {

    private Set<PedidoDto> pedidos = new HashSet<>();

}
