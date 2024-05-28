package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDetalleDto;
import com.example.buensaborback.domain.entities.PromocionDetalle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ArticuloMapper.class})
public interface PromocionDetalleMapper extends BaseMapper<PromocionDetalle, PromocionDetalleDto, PromocionDetalleDto>{
}
