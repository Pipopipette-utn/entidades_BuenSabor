package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.ProvinciaDto;
import com.example.buensaborback.entities.Provincia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProvinciaMapper extends IBaseMapper<Provincia, ProvinciaDto> {
    ProvinciaDto toDTO(Provincia provincia);
    Provincia toEntity(ProvinciaDto provinciaDto);
    List<ProvinciaDto> toDTOsList(List<Provincia> provinciaList);
    List<Provincia> toEntitiesList(List<ProvinciaDto> provinciaDtoList);
}
