package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.ImagenPersona;
import com.example.buensaborback.entities.dtos.ImagenPersonaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IImagenPersonaMapper extends IBaseMapper<ImagenPersona, ImagenPersonaDto> {
    ImagenPersonaDto toDTO(ImagenPersona source);
    ImagenPersona toEntity(ImagenPersonaDto source);
    List<ImagenPersonaDto> toDTOsList(List<ImagenPersona> source);
    List<ImagenPersona> toEntitiesList(List<ImagenPersonaDto> source);
}

