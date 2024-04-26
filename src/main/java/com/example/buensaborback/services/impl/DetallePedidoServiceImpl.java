package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IDetallePedidoService;
import com.example.buensaborback.entities.DetallePedido;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServiceImpl extends BaseServiceImpl<DetallePedido, Long> implements IDetallePedidoService {
    public DetallePedidoServiceImpl(BaseRepository<DetallePedido, Long> baseRepository) {
        super(baseRepository);
    }
}
