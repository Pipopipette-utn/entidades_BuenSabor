package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ImagenArticuloDto;
import com.example.buensaborback.domain.dto.PromocionDtos.ImagenPromocionDto;
import com.example.buensaborback.domain.entities.ImagenArticulo;
import com.example.buensaborback.domain.entities.ImagenPromocion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagenPromocionMapper extends BaseMapper<ImagenPromocion, ImagenPromocionDto, ImagenPromocionDto>{

}
