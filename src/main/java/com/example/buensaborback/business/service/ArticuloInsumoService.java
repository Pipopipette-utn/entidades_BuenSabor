package com.example.buensaborback.business.service;

import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ArticuloInsumoService extends BaseService<ArticuloInsumo,Long> {
    Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable);
    Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable);
    List<ArticuloInsumo> create(ArticuloInsumo request, Set<Sucursal> sucursales);
    Page<ArticuloInsumo> buscarPorCategoriaYNombre(Pageable pageable, Long idSucursal, Long categoriaId, String nombre);
    Page<ArticuloInsumo> getArticulosByCategoria(Pageable pageable, Long idSucursal, Long categoriaId);
    Page<ArticuloInsumo> getArticulosByNombre(Pageable pageable, Long idSucursal, String nombre);
    Page<ArticuloInsumo> findBySucursal(Long sucursalId, Pageable pageable);

    // Método que trae los insumos que no son para elaborar y dados de alta por sucursal
    Page<ArticuloInsumo> findBySucursalAltaAndElaborarFalse(Long sucursalId, Pageable pageable);

}
