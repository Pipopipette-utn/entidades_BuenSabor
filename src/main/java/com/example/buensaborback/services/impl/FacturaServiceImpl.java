package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IFacturaService;
import com.example.buensaborback.entities.Factura;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, Long> implements IFacturaService {
    public FacturaServiceImpl(BaseRepository<Factura, Long> baseRepository) {
        super(baseRepository);
    }
}
