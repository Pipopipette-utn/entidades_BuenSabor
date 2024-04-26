package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IArticuloManufacturadoService;
import com.example.buensaborback.entities.ArticuloManufacturado;
import com.example.buensaborback.repositories.BaseRepository;
import org.springframework.stereotype.Service;



@Service
public class ArticuloManufacturadoServiceImpl extends BaseServiceImpl<ArticuloManufacturado, Long> implements IArticuloManufacturadoService {
    public ArticuloManufacturadoServiceImpl(BaseRepository<ArticuloManufacturado, Long> baseRepository) {
        super(baseRepository);
    }
}
