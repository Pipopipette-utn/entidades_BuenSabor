package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.CategoriaDto;
import com.example.buensaborback.entities.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoriaMapper extends IBaseMapper<Categoria, CategoriaDto> {

    CategoriaDto toDTO(Categoria categoria);
    Categoria toEntity(CategoriaDto categoriaDto);
    List<CategoriaDto> toDTOsList(List<Categoria> categoriaList);
    List<Categoria> toEntitiesList(List<CategoriaDto> categoriaDtoList);
}
