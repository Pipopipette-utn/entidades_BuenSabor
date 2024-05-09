package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.DomicilioDto;
import com.example.buensaborback.entities.Domicilio;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDomicilioMapper extends IBaseMapper<Domicilio, DomicilioDto> {
    DomicilioDto toDTO(Domicilio domicilio);
    Domicilio toEntity(DomicilioDto domicilioDto);
    List<DomicilioDto> toDTOsList(List<Domicilio> domicilioList);
    List<Domicilio> toEntitiesList(List<DomicilioDto> domicilioDtoList);
}
