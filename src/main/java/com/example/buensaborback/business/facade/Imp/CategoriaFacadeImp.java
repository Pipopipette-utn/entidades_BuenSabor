package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.facade.CategoriaFacade;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.CategoriaMapper;
import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.business.service.CategoriaService;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaHomeDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaPostDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriaFacadeImp extends BaseFacadeImp<Categoria, CategoriaPostDto, CategoriaGetDto,  Long> implements CategoriaFacade {

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    CategoriaMapper categoriaMapper;

    @Autowired
    SucursalMapper sucursalMapper;

    public CategoriaFacadeImp(BaseService<Categoria, Long> baseService, BaseMapper<Categoria, CategoriaPostDto, CategoriaGetDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public Page<CategoriaGetDto> getCategoriaInsumos (Pageable pageable) {
        // Trae una página de entidades
        Page<Categoria> entities = categoriaService.findByEsInsumoTrue(pageable);
        // Mapea las entidades a DTOs
        List<CategoriaGetDto> dtos = entities.getContent().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<CategoriaGetDto> getCategoriaManufacturados (Pageable pageable) {
        // Trae una página de entidades
        Page<Categoria> entities = categoriaService.findByEsInsumoFalse(pageable);
        // Mapea las entidades a DTOs
        List<CategoriaGetDto> dtos = entities.getContent().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public void deleteInSucursales (Long id, SucursalShortDto shortSucursal) {
        Sucursal entity = sucursalMapper.toEntityShort(shortSucursal);
        categoriaService.deleteInSucursales(id, entity);
    }

    public List<CategoriaHomeDto> getAllParents(){
        var entities = categoriaService.getAllParents();
        //  devuelve una lista de DTO
        return entities
                .stream()
                .map(categoriaMapper::toHomeDTO)
                .collect(Collectors.toList());
    }

}
