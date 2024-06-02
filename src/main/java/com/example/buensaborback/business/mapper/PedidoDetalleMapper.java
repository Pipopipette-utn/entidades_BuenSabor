package com.example.buensaborback.business.mapper;


import com.example.buensaborback.domain.dto.PedidoDtos.PedidoDetalleDto;
import com.example.buensaborback.domain.entities.DetallePedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ArticuloMapper.class })
public interface PedidoDetalleMapper extends BaseMapper<DetallePedido, PedidoDetalleDto, PedidoDetalleDto> {

}
