package com.example.buensaborback.entities.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmpleadoDto extends BaseDto{
    private Set<PedidoDto> pedidos = new HashSet<>();
    private SucursalDto sucursal;
}
