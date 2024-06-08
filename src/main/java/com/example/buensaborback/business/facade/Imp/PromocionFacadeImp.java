package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.EmpleadoFacade;
import com.example.buensaborback.business.facade.PromocionFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.PromocionService;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.dto.EmpleadoDto;
import com.example.buensaborback.domain.dto.PromocionDtos.PromocionDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PromocionFacadeImp extends BaseFacadeImp<Promocion, PromocionDto,PromocionDto, Long> implements PromocionFacade {

    @Autowired
    private PromocionService promocionService;

    @Autowired
    SucursalMapper sucursalMapper;

    public PromocionFacadeImp(BaseService<Promocion, Long> baseService, BaseMapper<Promocion, PromocionDto, PromocionDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public void deleteInSucursales(Long id, Long idSucursal){
        promocionService.deleteInSucursales(id, idSucursal);
    }

    public List<PromocionDto> createConSucursales(PromocionDto request) {
        Set<SucursalShortDto> sucursalesDto = request.getSucursales();
        Set<Sucursal> sucursales = sucursalMapper.toEntitiesShort(sucursalesDto);
        Promocion entityToCreate = baseMapper.toEntity(request);
        // Graba una entidad
        var entityCreated = promocionService.create(entityToCreate, sucursales);
        // convierte a Dto para devolver
        return baseMapper.toDTOsList(entityCreated);
    }

    public List<PromocionDto> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales) {
        // Graba una entidad
        var entityCreated = promocionService.duplicateInOtherSucursales(id, sucursales);
        // convierte a Dto para devolver
        return baseMapper.toDTOsList(entityCreated);
    }

}
