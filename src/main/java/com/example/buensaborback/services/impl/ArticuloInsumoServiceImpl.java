package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IArticuloInsumoService;
import com.example.buensaborback.entities.ArticuloInsumo;
import com.example.buensaborback.repositories.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticuloInsumoServiceImpl extends BaseServiceImpl<ArticuloInsumo, Long> implements IArticuloInsumoService {
    public ArticuloInsumoServiceImpl(BaseRepository<ArticuloInsumo, Long> baseRepository) {
        super(baseRepository);
    }
}
