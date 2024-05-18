package com.example.buensaborback.business.mapper;

import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaPostDto;
import com.example.buensaborback.domain.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ArticuloMapper.class, SucursalMapper.class, SucursalService.class})
public interface CategoriaMapper extends BaseMapper<Categoria, CategoriaPostDto, CategoriaGetDto>{

    @Override
    CategoriaGetDto toDTO(Categoria source);

    @Override
    @Mapping(target = "sucursales", source = "sucursalIds", qualifiedByName = "getById")
    Categoria toEntity(CategoriaPostDto source);


}
