package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Pedido;
import com.example.buensaborback.domain.enums.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long>{
    Page<Pedido> findBySucursal_Id(Long sucursalId, Pageable pageable);
}
