package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ArticuloDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.entities.Articulo;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImagenArticuloMapper.class})
public interface ArticuloMapper extends BaseMapper<Articulo, ArticuloDto, ArticuloDto>{

}
