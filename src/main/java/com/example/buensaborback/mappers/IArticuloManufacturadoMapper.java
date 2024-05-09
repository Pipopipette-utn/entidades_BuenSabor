package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.ArticuloManufacturadoDto;
import com.example.buensaborback.entities.ArticuloManufacturado;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface IArticuloManufacturadoMapper extends IBaseMapper<ArticuloManufacturado, ArticuloManufacturadoDto>{

    ArticuloManufacturadoDto toDTO(ArticuloManufacturado articuloManufacturado);
    ArticuloManufacturado toEntity(ArticuloManufacturadoDto articuloManufacturadoDto);
    List<ArticuloManufacturadoDto> toDTOsList(List<ArticuloManufacturado> articuloManufacturadoList);
    List<ArticuloManufacturado> toEntitiesList(List<ArticuloManufacturadoDto> articuloManufacturadoDtoList);
}
