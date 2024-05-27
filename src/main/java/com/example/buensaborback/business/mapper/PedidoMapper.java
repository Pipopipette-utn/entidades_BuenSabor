package com.example.buensaborback.business.mapper;

import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDto;
import com.example.buensaborback.domain.entities.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { DomicilioMapper.class, SucursalMapper.class, PedidoDetalleMapper.class })
public interface PedidoMapper extends BaseMapper<Pedido, PedidoDto, PedidoDto> {

}
