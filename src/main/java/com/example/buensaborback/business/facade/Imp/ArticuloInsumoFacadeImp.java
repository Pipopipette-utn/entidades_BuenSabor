package com.example.buensaborback.business.facade.Imp;

import com.example.buensaborback.business.facade.ArticuloInsumoFacade;
import com.example.buensaborback.business.facade.ArticuloManufacturadoFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImp;
import com.example.buensaborback.business.mapper.ArticuloInsumoMapper;
import com.example.buensaborback.business.mapper.BaseMapper;
import com.example.buensaborback.business.mapper.SucursalMapper;
import com.example.buensaborback.business.service.ArticuloInsumoService;
import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.Articulo.ArticuloPostDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoDto;
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoPostDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloInsumoFacadeImp extends BaseFacadeImp<ArticuloInsumo, ArticuloInsumoDto, ArticuloInsumoDto, Long> implements ArticuloInsumoFacade {

    @Autowired
    ArticuloInsumoService articuloInsumoService;

    @Autowired
    ArticuloInsumoMapper articuloInsumoMapper;

    @Autowired
    SucursalMapper sucursalMapper;

    public ArticuloInsumoFacadeImp(BaseService<ArticuloInsumo, Long> baseService, BaseMapper<ArticuloInsumo, ArticuloInsumoDto, ArticuloInsumoDto> baseMapper) {
        super(baseService, baseMapper);
    }

    public Page<ArticuloInsumoDto> findByEsParaElaborarTrue(Pageable pageable) {
        // Trae una página de entidades
        Page<ArticuloInsumo> entities = articuloInsumoService.findByEsParaElaborarTrue(pageable);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = entities.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<ArticuloInsumoDto> findByEsParaElaborarFalse(Pageable pageable) {
        // Trae una página de entidades
        Page<ArticuloInsumo> entities = articuloInsumoService.findByEsParaElaborarFalse(pageable);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = entities.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<ArticuloInsumoDto> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre) {
        Page<ArticuloInsumo> articulosFiltrados = articuloInsumoService.buscarPorCategoriaYNombre(pageable, idSucursal, categoriaId, nombre);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public List<ArticuloInsumoDto> createConSucursales(ArticuloInsumoPostDto request) {
        Set<SucursalShortDto> sucursalesDto = request.getSucursales();
        Set<Sucursal> sucursales = sucursalMapper.toEntitiesShort(sucursalesDto);
        ArticuloInsumo entityToCreate = articuloInsumoMapper.toEntityArticuloInsumo(request);
        // Graba una entidad
        var entityCreated = articuloInsumoService.create(entityToCreate, sucursales);
        // convierte a Dto para devolver
        return articuloInsumoMapper.toPostDtoList(entityCreated);
    }

    public List<ArticuloInsumoDto> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales) {
        // Graba una entidad
        var entityCreated = articuloInsumoService.duplicateInOtherSucursales(id, sucursales);
        // convierte a Dto para devolver
        return articuloInsumoMapper.toPostDtoList(entityCreated);
    }

    public Page<ArticuloInsumoDto> getArticulosByCategoria(Pageable pageable, Long idSucursal,Long categoriaId) {
        Page<ArticuloInsumo> articulosFiltrados = articuloInsumoService.getArticulosByCategoria(pageable, idSucursal, categoriaId);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public Page<ArticuloInsumoDto> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre) {
        Page<ArticuloInsumo> articulosFiltrados = articuloInsumoService.getArticulosByNombre(pageable, idSucursal, nombre);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public Page<ArticuloInsumoDto> findBySucursal(Long sucursalId, Pageable pageable) {
        Page<ArticuloInsumo> articulosFiltrados = articuloInsumoService.findBySucursal(sucursalId, pageable);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public List<ArticuloInsumoDto> findBySucursal(Long sucursalId) {
        List<ArticuloInsumo> articulosFiltrados = articuloInsumoService.findBySucursal(sucursalId);
        // Mapea las entidades a DTOs
        List<ArticuloInsumoDto> dtos = articulosFiltrados.stream()
                .map(articuloInsumoMapper::toDTO)
                .collect(Collectors.toList());
        return dtos;
    }
}
