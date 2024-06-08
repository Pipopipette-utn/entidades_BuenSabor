package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.domain.entities.Sucursal;

import java.util.List;
import java.util.Set;

public interface PromocionService extends BaseService<Promocion,Long> {
    void deleteInSucursales(Long id, Long idSucursal);
    List<Promocion> create(Promocion request, Set<Sucursal> sucursales);
    List<Promocion> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales);


}
