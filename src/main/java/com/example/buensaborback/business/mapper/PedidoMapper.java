package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetClienteDto;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoGetDto;
import com.example.buensaborback.domain.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = { DomicilioMapper.class, SucursalMapper.class, PedidoDetalleMapper.class })
public interface PedidoMapper extends BaseMapper<Pedido, PedidoDto, PedidoGetDto> {
    Pedido toEntityEstado (PedidoEstadoDto source);


    PedidoEstadoDto toDtoEstado (Pedido pedido);
    Set<PedidoGetClienteDto> toDtoPedidoCliente(Set<Pedido> pedido);
}
