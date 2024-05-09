package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.PromocionDto;
import com.example.buensaborback.entities.Promocion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPromocionMapper extends IBaseMapper<Promocion, PromocionDto> {

    PromocionDto toDTO(Promocion promocion);
    Promocion toEntity(PromocionDto promocionDto);
    List<PromocionDto> toDTOsList(List<Promocion> promocionList);
    List<Promocion> toEntitiesList(List<PromocionDto> promocionDtoList);
}
