package com.example.buensaborback.entities.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ArticuloManufacturadoDto extends ArticuloDto {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

    private List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalles = new ArrayList<>();
}
