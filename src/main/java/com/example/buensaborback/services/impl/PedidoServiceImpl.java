package com.example.buensaborback.services.impl;

import com.example.buensaborback.entities.Pedido;
import com.example.buensaborback.repositories.BaseRepository;
import com.example.buensaborback.services.IPedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido, Long> implements IPedidoService {
    public PedidoServiceImpl(BaseRepository<Pedido, Long> baseRepository) {
        super(baseRepository);
    }
}
