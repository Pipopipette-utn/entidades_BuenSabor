package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado,Long> {
    Page<ArticuloManufacturado> findBySucursal_IdAndCategoria_IdAndDenominacionContainingIgnoreCase(Long sucursalId, Long categoriaId, String denominacion, Pageable pageable);
    Page<ArticuloManufacturado> findBySucursal_IdAndCategoria_Id(Long sucursalId, Long categoriaId, Pageable pageable);
    Page<ArticuloManufacturado> findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion, Pageable pageable);
    Page<ArticuloManufacturado> findBySucursal_Id(Long sucursalId, Pageable pageable);
}
