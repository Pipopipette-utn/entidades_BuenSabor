package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaHomeDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaPostDto;
import com.example.buensaborback.domain.entities.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ArticuloMapper.class, SucursalMapper.class})
public interface CategoriaMapper extends BaseMapper<Categoria, CategoriaPostDto, CategoriaGetDto>{

    @Override
    CategoriaGetDto toDTO(Categoria source);

    @Override
    Categoria toEntity(CategoriaPostDto source);

    CategoriaHomeDto toHomeDTO(Categoria source);


}
