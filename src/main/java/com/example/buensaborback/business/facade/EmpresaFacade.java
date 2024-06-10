package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.domain.dto.EmpresaDtos.EmpresaDto;
import com.example.buensaborback.domain.dto.EmpresaDtos.EmpresaLargeDto;


public interface EmpresaFacade extends BaseFacade<EmpresaDto,EmpresaDto, Long> {
    EmpresaLargeDto addSucursal(Long idEmpresa, Long idSucursal);
}
