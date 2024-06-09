package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.dto.SucursalDtos.SucursalShortDto;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ArticuloManufacturadoService extends BaseService<ArticuloManufacturado,Long> {
    List<ArticuloManufacturado> create(ArticuloManufacturado request, Set<Sucursal> sucursales);
    List<ArticuloManufacturado> duplicateInOtherSucursales(Long id, Set<SucursalShortDto> sucursales);
    Page<ArticuloManufacturado> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre);
    Page<ArticuloManufacturado> getArticulosByCategoria(Pageable pageable, Long idSucursal, Long categoriaId);
    Page<ArticuloManufacturado> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre);
    Page<ArticuloManufacturado> findBySucursal(Long sucursalId, Pageable pageable);
    List<ArticuloManufacturado> findBySucursal(Long sucursalId);
}
