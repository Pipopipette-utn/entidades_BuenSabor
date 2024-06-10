package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.ClienteDto.ClienteDto;
import com.example.buensaborback.domain.dto.ClienteDto.ClienteLoginDto;
import com.example.buensaborback.domain.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDto, ClienteDto>{

    Cliente entityLogin(ClienteLoginDto source);
}
