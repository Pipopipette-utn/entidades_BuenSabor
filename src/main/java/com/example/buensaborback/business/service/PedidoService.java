package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService extends BaseService<Pedido,Long> {
    Pedido cambiarEstado(Pedido request, Long id) throws Exception;
    Page<Pedido> findBySucursalAndEstado(Long sucursalId, Estado estado, int page, int size);

}
