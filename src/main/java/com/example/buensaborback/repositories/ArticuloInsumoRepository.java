package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.ArticuloInsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo,Long> {
    Page<ArticuloInsumo> findByEsParaElaborarTrue(Pageable pageable);
    Page<ArticuloInsumo> findByEsParaElaborarFalse(Pageable pageable);

    @Query("SELECT ai FROM ArticuloInsumo ai WHERE ai.sucursal.id = :sucursalId AND ai.categoria.id IN :categoriaIds AND ai.denominacion LIKE %:denominacion%")
    Page<ArticuloInsumo> findBySucursalIdAndCategoriaIdInAndDenominacionContainingIgnoreCase(@Param("sucursalId") Long sucursalId, @Param("categoriaIds") List<Long> categoriaIds, @Param("denominacion") String denominacion, Pageable pageable);

    @Query("SELECT ai FROM ArticuloInsumo ai WHERE ai.sucursal.id = :sucursalId AND ai.categoria.id IN :categoriaIds")
    Page<ArticuloInsumo> findBySucursalIdAndCategoriaIdIn(@Param("sucursalId") Long sucursalId, @Param("categoriaIds") List<Long> categoriaIds, Pageable pageable);
    Page<ArticuloInsumo> findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion, Pageable pageable);

    ArticuloInsumo findBySucursal_IdAndDenominacionContainingIgnoreCase(Long sucursalId, String denominacion);

    Page<ArticuloInsumo> findBySucursal_Id(Long sucursalId, Pageable pageable);
    List<ArticuloInsumo> findBySucursal_IdAndBajaFalse(Long sucursalId);

}
