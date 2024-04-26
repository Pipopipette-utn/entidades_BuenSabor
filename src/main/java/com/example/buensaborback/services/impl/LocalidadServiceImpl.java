package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.ILocalidadService;
import com.example.buensaborback.entities.Localidad;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements ILocalidadService {
    public LocalidadServiceImpl(BaseRepository<Localidad, Long> baseRepository) {
        super(baseRepository);
    }
}
