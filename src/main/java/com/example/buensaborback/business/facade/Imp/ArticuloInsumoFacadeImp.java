package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ArticuloInsumoFacade;
import com.example.buensaborback.business.facade.ArticuloManufacturadoFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import org.springframework.stereotype.Service;

@Service
public class ArticuloInsumoFacadeImp extends BaseFacadeImp<ArticuloInsumo, ArticuloInsumoDto, Long> implements ArticuloInsumoFacade {
    public ArticuloInsumoFacadeImp(BaseService<ArticuloInsumo, Long> baseService, BaseMapper<ArticuloInsumo, ArticuloInsumoDto> baseMapper) {
        super(baseService, baseMapper);
    }
}
