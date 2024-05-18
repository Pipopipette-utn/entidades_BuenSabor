package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ArticuloManufacturadoDetalleMapper.class })
public interface ArticuloManufacturadoMapper extends BaseMapper<ArticuloManufacturado, ArticuloManufacturadoDto, ArticuloManufacturadoDto>{

}
