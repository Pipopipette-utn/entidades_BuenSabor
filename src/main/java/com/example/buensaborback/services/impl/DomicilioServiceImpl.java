package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IDomicilioService;
import com.example.buensaborback.entities.Domicilio;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<Domicilio, Long> implements IDomicilioService {
    public DomicilioServiceImpl(BaseRepository<Domicilio, Long> baseRepository) {
        super(baseRepository);
    }
}