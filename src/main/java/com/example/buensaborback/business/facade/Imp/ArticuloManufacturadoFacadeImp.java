package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ArticuloManufacturadoFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.ArticuloManufacturadoMapper;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.EmpleadoDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoFacadeImp extends BaseFacadeImp<ArticuloManufacturado, ArticuloManufacturadoDto,ArticuloManufacturadoDto, Long> implements ArticuloManufacturadoFacade {

    @Autowired
    ArticuloManufacturadoService articuloManufacturadoService;

    @Autowired
    ArticuloManufacturadoMapper articuloManufacturadoMapper;

    public ArticuloManufacturadoFacadeImp(BaseService<ArticuloManufacturado, Long> baseService, BaseMapper<ArticuloManufacturado, ArticuloManufacturadoDto, ArticuloManufacturadoDto> baseMapper) {
        super(baseService, baseMapper);
    }

    @Override
    public ArticuloManufacturadoDto update(ArticuloManufacturadoDto request, Long id) {
        var entityUpdated = articuloManufacturadoService.update(request, id);
        return articuloManufacturadoMapper.toDTO(entityUpdated);
    }
}
