package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.ArticuloInsumo;
import com.example.buensaborback.domain.entities.ArticuloManufacturado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoRepository extends BaseRepository<ArticuloManufacturado,Long> {
    @Query("SELECT ai FROM ArticuloManufacturado ai WHERE ai.sucursal.id = :sucursalId AND ai.categoria.id IN :categoriaIds AND ai.denominacion LIKE %:denominacion%")
    Page<ArticuloManufacturado> findBySucursalIdAndCategoriaIdInAndDenominacionContainingIgnoreCase(@Param("sucursalId") Long sucursalId, @Param("categoriaIds") List<Long> categoriaIds, @Param("denominacion") String denominacion, Pageable pageable);

    @Query("SELECT ai FROM ArticuloManufacturado ai WHERE ai.sucursal.id = :sucursalId AND ai.categoria.id IN :categoriaIds")
    Page<ArticuloManufacturado> findBySucursalIdAndCategoriaIdIn(@Param("sucursalId") Long sucursalId, @Param("categoriaIds") List<Long> categoriaIds, Pageable pageable);

    ArticuloManufacturado findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion);

    Page<ArticuloManufacturado> findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion, Pageable pageable);
    Page<ArticuloManufacturado> findBySucursal_Id(Long sucursalId, Pageable pageable);
    List<ArticuloManufacturado> findBySucursal_IdAndBajaFalse(Long sucursalId);
}
