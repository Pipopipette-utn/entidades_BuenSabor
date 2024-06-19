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
import com.example.buensaborback.domain.dto.ArticuloInsumoDtos.ArticuloInsumoPostDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.example.buensaborback.domain.dto.ArticuloManufacturado.ArticuloManufacturadoPostDto;
import com.example.buensaborback.domain.dto.CategoriaDtos.CategoriaGetDto;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Empleado;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<ArticuloManufacturadoDto> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales) {
        // Graba una entidad
        var entityCreated = articuloManufacturadoService.duplicateInOtherSucursales(id, sucursales);
        // convierte a Dto para devolver
        return articuloManufacturadoMapper.toDTOsList(entityCreated);
    }

    public Page<ArticuloManufacturadoDto> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre) {
        Page<ArticuloManufacturado> articulosFiltrados = articuloManufacturadoService.buscarPorCategoriaYNombre(pageable, idSucursal, categoriaId, nombre);
        // Mapea las entidades a DTOs
        List<ArticuloManufacturadoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloManufacturadoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public Page<ArticuloManufacturadoDto> getArticulosByCategoria(Pageable pageable, Long idSucursal,Long categoriaId) {
        Page<ArticuloManufacturado> articulosFiltrados = articuloManufacturadoService.getArticulosByCategoria(pageable, idSucursal, categoriaId);
        // Mapea las entidades a DTOs
        List<ArticuloManufacturadoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloManufacturadoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public Page<ArticuloManufacturadoDto> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre) {
        Page<ArticuloManufacturado> articulosFiltrados = articuloManufacturadoService.getArticulosByNombre(pageable, idSucursal, nombre);
        // Mapea las entidades a DTOs
        List<ArticuloManufacturadoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloManufacturadoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public Page<ArticuloManufacturadoDto> findBySucursal(Long sucursalId, Pageable pageable) {
        Page<ArticuloManufacturado> articulosFiltrados = articuloManufacturadoService.findBySucursal(sucursalId, pageable);
        // Mapea las entidades a DTOs
        List<ArticuloManufacturadoDto> dtos = articulosFiltrados.getContent().stream()
                .map(articuloManufacturadoMapper::toDTO)
                .collect(Collectors.toList());
        // Devuelve una página de DTOs
        return new PageImpl<>(dtos, pageable, articulosFiltrados.getTotalElements());
    }

    public List<ArticuloManufacturadoDto> findBySucursal(Long sucursalId) {
        List<ArticuloManufacturado> articulosFiltrados = articuloManufacturadoService.findBySucursal(sucursalId);
        // Mapea las entidades a DTOs
        List<ArticuloManufacturadoDto> dtos = articulosFiltrados.stream()
                .map(articuloManufacturadoMapper::toDTO)
                .collect(Collectors.toList());
        return dtos;
    }
}
