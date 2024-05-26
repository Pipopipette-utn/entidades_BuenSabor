package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;

public interface ArticuloManufacturadoService extends BaseService<ArticuloManufacturado,Long> {
    ArticuloManufacturado update(ArticuloManufacturadoDto request, Long id);
}
