package com.example.buensaborback.mappers;

import com.example.buensaborback.entities.dtos.PedidoDto;
import com.example.buensaborback.entities.Pedido;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IPedidoMapper extends IBaseMapper<Pedido,PedidoDto> {

    PedidoDto toDTO(Pedido pedido);
    Pedido toEntity(PedidoDto pedidoDto);
    List<PedidoDto> toDTOsList(List<Pedido> pedidoList);
    List<Pedido> toEntitiesList(List<PedidoDto> pedidoDtosList);
}
