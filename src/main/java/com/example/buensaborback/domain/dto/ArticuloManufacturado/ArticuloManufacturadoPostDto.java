package com.example.buensaborback.domain.dto.ArticuloManufacturado;

import com.example.buensaborback.domain.dto.Articulo.ArticuloPostDto;
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
public class ArticuloManufacturadoPostDto extends ArticuloPostDto {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;


    private Set<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalles = new HashSet<>();

}
