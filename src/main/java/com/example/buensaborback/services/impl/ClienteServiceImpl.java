package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IClienteService;
import com.example.buensaborback.entities.Cliente;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, Long> implements IClienteService {
    public ClienteServiceImpl(BaseRepository<Cliente, Long> baseRepository) {
        super(baseRepository);
    }
}
