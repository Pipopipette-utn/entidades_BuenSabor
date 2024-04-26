package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IUnidadMedidaService;
import com.example.buensaborback.entities.UnidadMedida;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaServiceImpl extends BaseServiceImpl<UnidadMedida, Long> implements IUnidadMedidaService {
    public UnidadMedidaServiceImpl(BaseRepository<UnidadMedida, Long> baseRepository) {
        super(baseRepository);
    }
}
