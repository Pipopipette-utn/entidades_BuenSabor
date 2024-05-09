package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.ClienteDto;
import com.example.buensaborback.entities.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClienteMapper extends IBaseMapper<Cliente, ClienteDto> {

    ClienteDto toDTO(Cliente cliente);
    Cliente toEntity(ClienteDto clienteDto);
    List<ClienteDto> toDTOsList(List<Cliente> clienteList);
    List<Cliente> toEntitiesList(List<ClienteDto> clienteDtoList);
}
