package com.example.buensaborback.domain.dto.Articulo;

import com.example.buensaborback.domain.dto.BaseDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaDto;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.dto.UnidadMedidaDto;
import com.example.buensaborback.domain.entities.Categoria;
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
    protected CategoriaDto categoria;
}
