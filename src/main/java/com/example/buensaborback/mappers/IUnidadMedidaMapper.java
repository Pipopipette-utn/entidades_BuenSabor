package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.UnidadMedidaDto;
import com.example.buensaborback.entities.UnidadMedida;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUnidadMedidaMapper extends IBaseMapper<UnidadMedida, UnidadMedidaDto> {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "baja", target = "baja")
    })
    UnidadMedidaDto toDTO(UnidadMedida unidadMedida);

    @InheritInverseConfiguration
    UnidadMedida toEntity(UnidadMedidaDto unidadMedidaDto);
    List<UnidadMedidaDto> toDTOsList(List<UnidadMedida> unidadMedidaList);
    List<UnidadMedida> toEntitiesList(List<UnidadMedidaDto> unidadMedidaDtoList);
}
