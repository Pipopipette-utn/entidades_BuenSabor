package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IPromocionService;
import com.example.buensaborback.entities.Promocion;
import com.example.buensaborback.repositories.BaseRepository;

import org.springframework.stereotype.Service;

@Service
public class PromocionServiceImpl extends BaseServiceImpl<Promocion, Long> implements IPromocionService {
    public PromocionServiceImpl(BaseRepository<Promocion, Long> baseRepository) {
        super(baseRepository);
    }
}
