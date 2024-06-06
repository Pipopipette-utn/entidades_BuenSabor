package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.PedidoDtos.PedidoEstadoDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService extends BaseService<Pedido,Long> {
    Pedido cambiarEstado(Pedido request, Long id);
    Page<Pedido> findBySucursal(Long sucursalId, Pageable pageable);

}
