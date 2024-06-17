package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.EmpresaDtos.ImagenEmpresaDto;
import com.example.buensaborback.domain.dto.ImagenPersonaDto;
import com.example.buensaborback.domain.entities.ImagenEmpresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagenEmpresaMapper extends BaseMapper<ImagenEmpresa, ImagenEmpresaDto, ImagenPersonaDto>{

}
