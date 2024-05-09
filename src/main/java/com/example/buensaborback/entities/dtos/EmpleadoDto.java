package com.example.buensaborback.entities.dtos;

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
public class EmpleadoDto extends BaseDto{
    private Set<PedidoDto> pedidos = new HashSet<>();
    private SucursalDto sucursal;
}
