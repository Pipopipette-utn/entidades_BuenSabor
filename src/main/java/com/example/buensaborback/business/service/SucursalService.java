package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Sucursal;

public interface SucursalService  extends BaseService<Sucursal, Long> {
    Sucursal guardarSucursal(Sucursal sucursal);
    Sucursal actualizarSucursal(Long id,Sucursal sucursal);
}

