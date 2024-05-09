package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.Empleado;
import com.example.buensaborback.entities.Empresa;
import com.example.buensaborback.entities.dtos.EmpleadoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmpleadoMapper extends IBaseMapper<Empleado, EmpleadoDto> {
    EmpleadoDto toDTO(Empleado empleado);
    Empleado toEntity(EmpleadoDto empleadoDto);
    List<EmpleadoDto> toDTOsList(List<Empleado> empleadoList);
    List<Empleado> toEntitiesList(List<EmpleadoDto> empleadoDtoList);
}
