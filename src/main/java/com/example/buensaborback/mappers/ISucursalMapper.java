package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.SucursalDto;
import com.example.buensaborback.entities.Sucursal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISucursalMapper extends IBaseMapper<Sucursal, SucursalDto> {
    SucursalDto toDTO(Sucursal sucursal);
    Sucursal toEntity(SucursalDto sucursalDto);
    List<SucursalDto> toDTOsList(List<Sucursal> sucursalList);
    List<Sucursal> toEntitiesList(List<SucursalDto> sucursalDtoList);
}