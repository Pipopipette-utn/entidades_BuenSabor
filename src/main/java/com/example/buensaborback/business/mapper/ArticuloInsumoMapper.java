package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloInsumoMapper extends BaseMapper<ArticuloInsumo, ArticuloInsumoDto>{

}
