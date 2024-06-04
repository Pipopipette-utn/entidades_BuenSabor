package com.example.buensaborback.domain.dto.ArticuloInsumoDtos;

import com.example.buensaborback.domain.dto.Articulo.ArticuloPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticuloInsumoPostDto extends ArticuloPostDto {
    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private Boolean esParaElaborar;
}
