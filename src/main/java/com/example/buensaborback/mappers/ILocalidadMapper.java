package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.LocalidadDto;
import com.example.buensaborback.entities.Localidad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ILocalidadMapper extends IBaseMapper<Localidad, LocalidadDto> {
    LocalidadDto toDTO(Localidad localidad);
    Localidad toEntity(LocalidadDto localidadDto);
    List<LocalidadDto> toDTOsList(List<Localidad> localidadList);
    List<Localidad> toEntitiesList(List<LocalidadDto> localidadDtoList);
}