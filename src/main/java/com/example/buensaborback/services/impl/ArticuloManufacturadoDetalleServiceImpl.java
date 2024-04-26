package com.example.buensaborback.services.impl;

import com.example.buensaborback.services.IArticuloManufacturadoDetalleService;
import com.example.buensaborback.entities.ArticuloManufacturadoDetalle;
import com.example.buensaborback.repositories.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoDetalleServiceImpl extends BaseServiceImpl<ArticuloManufacturadoDetalle, Long> implements IArticuloManufacturadoDetalleService {
    public ArticuloManufacturadoDetalleServiceImpl(BaseRepository<ArticuloManufacturadoDetalle, Long> baseRepository) {
        super(baseRepository);
    }
}
