package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.PaisDto;
import com.example.buensaborback.entities.Pais;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPaisMapper extends IBaseMapper<Pais, PaisDto> {
    PaisDto toDTO(Pais pais);
    Pais toEntity(PaisDto paisDto);
    List<PaisDto> toDTOsList(List<Pais> paisList);
    List<Pais> toEntitiesList(List<PaisDto> paisDtoList);
}
