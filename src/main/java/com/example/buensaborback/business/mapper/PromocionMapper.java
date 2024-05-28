package com.example.buensaborback.business.mapper;

import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDto;
import com.example.buensaborback.domain.entities.Promocion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImagenPromocionMapper.class, PromocionDetalleMapper.class, SucursalMapper.class})
public interface PromocionMapper extends BaseMapper<Promocion, PromocionDto, PromocionDto>{
}
