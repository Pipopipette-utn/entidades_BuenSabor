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
public class CategoriaDto extends BaseDto{
    private String denominacion;
    private Set<ArticuloDto> articulos = new HashSet<>();
    private Set<CategoriaDto> subCategorias = new HashSet<>();
    private CategoriaDto categoriaPadre;
}
