package com.example.buensaborback.entities.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetallePedidoDto extends BaseDto {

    private Integer cantidad;
    private Double subTotal;
    private PedidoDto pedido;
    private ArticuloDto articulo;
}
