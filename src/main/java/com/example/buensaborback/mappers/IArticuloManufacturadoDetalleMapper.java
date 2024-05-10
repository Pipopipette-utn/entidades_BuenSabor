package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.ArticuloManufacturado;
import com.example.buensaborback.entities.ArticuloManufacturadoDetalle;
import com.example.buensaborback.entities.dtos.ArticuloManufacturadoDetalleDto;
import com.example.buensaborback.entities.dtos.ArticuloManufacturadoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IArticuloManufacturadoDetalleMapper extends IBaseMapper<ArticuloManufacturadoDetalle, ArticuloManufacturadoDetalleDto>{

    ArticuloManufacturadoDetalleDto toDTO(ArticuloManufacturadoDetalle articuloManufacturadoDetalle);
    ArticuloManufacturadoDetalle toEntity(ArticuloManufacturadoDetalleDto articuloManufacturadoDetalleDto);
    List<ArticuloManufacturadoDetalleDto> toDTOsList(List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalleList);
    List<ArticuloManufacturadoDetalle> toEntitiesList(List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalleDtosList);
}
