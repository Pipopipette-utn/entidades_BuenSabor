package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.EmpresaDto;
import com.example.buensaborback.entities.Empresa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmpresaMapper extends IBaseMapper<Empresa, EmpresaDto> {
    EmpresaDto toDTO(Empresa empresa);
    Empresa toEntity(EmpresaDto empresaDto);
    List<EmpresaDto> toDTOsList(List<Empresa> empresaList);
    List<Empresa> toEntitiesList(List<EmpresaDto> empresaDtoList);
}
