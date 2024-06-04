package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Sucursal;

import java.util.List;
import java.util.Set;

public interface ArticuloManufacturadoService extends BaseService<ArticuloManufacturado,Long> {
    List<ArticuloManufacturado> create(ArticuloManufacturado request, Set<Sucursal> sucursales);
}
