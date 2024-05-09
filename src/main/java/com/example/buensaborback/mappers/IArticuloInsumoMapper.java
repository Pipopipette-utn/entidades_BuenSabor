package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.ArticuloInsumoDto;
import com.example.buensaborback.entities.ArticuloInsumo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticuloInsumoMapper extends IBaseMapper<ArticuloInsumo, ArticuloInsumoDto> {

    ArticuloInsumoDto toDTO(ArticuloInsumo articuloInsumo);
    ArticuloInsumo toEntity(ArticuloInsumoDto articuloInsumoDto);
    List<ArticuloInsumoDto> toDTOsList(List<ArticuloInsumo> articuloInsumoList);
    List<ArticuloInsumo> toEntitiesList(List<ArticuloInsumoDto> articuloInsumoDtoList);
}
