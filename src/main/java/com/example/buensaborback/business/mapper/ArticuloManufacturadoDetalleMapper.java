package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDetalleDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturadoDetalle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ArticuloInsumoMapper.class})
public interface ArticuloManufacturadoDetalleMapper extends BaseMapper<ArticuloManufacturadoDetalle, ArticuloManufacturadoDetalleDto, ArticuloManufacturadoDetalleDto>{

}
