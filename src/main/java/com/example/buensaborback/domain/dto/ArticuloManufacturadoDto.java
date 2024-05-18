package com.example.buensaborback.domain.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticuloManufacturadoDto extends ArticuloDto {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;


    private Set<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalles = new HashSet<>();

}
