package com.example.buensaborback.entities.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ArticuloManufacturadoDetalleDto extends BaseDto{

    private Integer cantidad;
    private ArticuloInsumoDto articuloInsumo;

}
