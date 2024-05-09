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
public class ArticuloDto extends BaseDto{

    protected String denominacion;
    protected Double precioVenta;
    private Set<ImagenArticuloDto> imagenes = new HashSet<>();
    private CategoriaDto categoria;
    private UnidadMedidaDto unidadMedida;

}
