package com.example.buensaborback.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetallePedidoDto extends BaseDto {

    private Integer cantidad;
    private Double subTotal;
    private PedidoDto pedido;
    private ArticuloDto articulo;
}
