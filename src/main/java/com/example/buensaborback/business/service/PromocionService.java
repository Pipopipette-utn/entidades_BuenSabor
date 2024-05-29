package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.domain.entities.Sucursal;

public interface PromocionService extends BaseService<Promocion,Long> {
    void deleteInSucursales(Long id, Long idSucursal);
}
