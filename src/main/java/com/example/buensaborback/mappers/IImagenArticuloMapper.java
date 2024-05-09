package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.ImagenArticuloDto;
import com.example.buensaborback.entities.ImagenArticulo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IImagenArticuloMapper extends IBaseMapper<ImagenArticulo, ImagenArticuloDto> {
    ImagenArticuloDto toDTO(ImagenArticulo source);
    ImagenArticulo toEntity(ImagenArticuloDto source);
    List<ImagenArticuloDto> toDTOsList(List<ImagenArticulo> source);
    List<ImagenArticulo> toEntitiesList(List<ImagenArticuloDto> source);
}

