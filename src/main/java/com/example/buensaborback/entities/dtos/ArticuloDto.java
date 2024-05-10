package com.example.buensaborback.entities.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ArticuloDto extends BaseDto{

    protected String denominacion;
    protected Double precioVenta;
    private Set<ImagenArticuloDto> imagenes = new HashSet<>();
    private CategoriaDto categoria;
    private UnidadMedidaDto unidadMedida;

}
