package com.example.buensaborback.business.mapper;

import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.domain.dto.ArticuloDto;
import com.example.buensaborback.domain.entities.Articulo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ImagenArticuloMapper.class, CategoriaService.class, CategoriaMapper.class})
public interface ArticuloMapper extends BaseMapper<Articulo, ArticuloDto, ArticuloDto>{
    @Override
    Articulo toEntity(ArticuloDto source);
}
