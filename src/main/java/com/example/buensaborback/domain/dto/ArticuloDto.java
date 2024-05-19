package com.example.buensaborback.domain.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloDto extends BaseDto {
    protected String denominacion;
    protected Double precioVenta;
    protected Set<ImagenArticuloDto> imagenes = new HashSet<>();
    protected UnidadMedidaDto unidadMedida;
    protected Long categoriaId;
}
