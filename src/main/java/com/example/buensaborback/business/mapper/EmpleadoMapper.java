package com.example.buensaborback.business.mapper;


import com.example.buensaborback.domain.dto.EmpleadoDtos.EmpleadoPostDto;
import com.example.buensaborback.domain.entities.Empleado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper extends BaseMapper<Empleado, EmpleadoPostDto, EmpleadoPostDto> {

}
