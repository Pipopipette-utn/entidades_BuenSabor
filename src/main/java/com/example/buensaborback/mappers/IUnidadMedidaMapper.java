package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.UnidadMedidaDto;
import com.example.buensaborback.entities.UnidadMedida;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUnidadMedidaMapper extends IBaseMapper<UnidadMedida, UnidadMedidaDto> {
    UnidadMedidaDto toDTO(UnidadMedida unidadMedida);
    UnidadMedida toEntity(UnidadMedidaDto unidadMedidaDto);
    List<UnidadMedidaDto> toDTOsList(List<UnidadMedida> unidadMedidaList);
    List<UnidadMedida> toEntitiesList(List<UnidadMedidaDto> unidadMedidaDtoList);
}
