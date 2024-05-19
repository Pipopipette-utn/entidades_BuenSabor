package com.example.buensaborback.business.mapper;

import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.domain.dto.ArticuloDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImagenArticuloMapper.class, CategoriaService.class})
public interface ArticuloMapper extends BaseMapper<Articulo, ArticuloDto, ArticuloDto>{

    @Override
    @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "getById")
    Articulo toEntity(ArticuloDto source);
}
