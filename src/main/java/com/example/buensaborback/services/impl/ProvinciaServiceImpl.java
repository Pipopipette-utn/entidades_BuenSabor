package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IProvinciaService;
import com.example.buensaborback.entities.Provincia;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class ProvinciaServiceImpl extends BaseServiceImpl<Provincia, Long> implements IProvinciaService {
    public ProvinciaServiceImpl(BaseRepository<Provincia, Long> baseRepository) {
        super(baseRepository);
    }
}
