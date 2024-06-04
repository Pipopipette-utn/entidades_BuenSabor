package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ArticuloManufacturadoFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.mapper.ArticuloManufacturadoMapper;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.service.ArticuloManufacturadoService;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.Articulo.ArticuloPostDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.dto.EmpleadoDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArticuloManufacturadoFacadeImp extends BaseFacadeImp<ArticuloManufacturado, ArticuloManufacturadoDto,ArticuloManufacturadoDto, Long> implements ArticuloManufacturadoFacade {

    @Autowired
    ArticuloManufacturadoService articuloManufacturadoService;

    @Autowired
    ArticuloManufacturadoMapper articuloManufacturadoMapper;

    @Autowired
    SucursalMapper sucursalMapper;

    public ArticuloManufacturadoFacadeImp(BaseService<ArticuloManufacturado, Long> baseService, BaseMapper<ArticuloManufacturado, ArticuloManufacturadoDto, ArticuloManufacturadoDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public List<ArticuloManufacturadoDto> createConSucursales(ArticuloManufacturadoPostDto request) {
        Set<SucursalShortDto> sucursalesDto = request.getSucursales();
        Set<Sucursal> sucursales = sucursalMapper.toEntitiesShort(sucursalesDto);
        ArticuloManufacturado entityToCreate = articuloManufacturadoMapper.toEntityArticuloManufacturado(request);
        // Graba una entidad
        var entityCreated = articuloManufacturadoService.create(entityToCreate, sucursales);
        // convierte a Dto para devolver
        return articuloManufacturadoMapper.toDTOsList(entityCreated);
    }
}
