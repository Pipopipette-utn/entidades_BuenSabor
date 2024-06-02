package com.example.buensaborback.domain.dto.PedidoDtos;

import com.example.buensaborback.domain.dto.Articulo.ArticuloShortDto;
import com.example.buensaborback.domain.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoDetalleDto extends BaseDto {
    private Integer cantidad;
    private Double subTotal;

    private ArticuloShortDto articulo;
}
