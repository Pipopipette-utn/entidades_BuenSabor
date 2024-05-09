package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.UsuarioDto;
import com.example.buensaborback.entities.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUsuarioMapper extends IBaseMapper<Usuario, UsuarioDto> {
    UsuarioDto toDTO(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);
    List<UsuarioDto> toDTOsList(List<Usuario> usuarioList);
    List<Usuario> toEntitiesList(List<UsuarioDto> usuarioDtoList);
}
