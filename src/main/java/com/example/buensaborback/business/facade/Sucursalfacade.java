package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dto.SucursalDto;

public interface Sucursalfacade extends BaseFacade<SucursalDto, Long> {
    SucursalDto createSucursal(SucursalDto dto);
    SucursalDto updateSucursal(Long id,SucursalDto dto);
}